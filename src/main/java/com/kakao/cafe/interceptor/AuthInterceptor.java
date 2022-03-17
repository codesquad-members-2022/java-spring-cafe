package com.kakao.cafe.interceptor;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException, ServletException {

        debug(request);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInfo");

        if (user == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        String writer = request.getParameter("writer");
        String currentURI = request.getRequestURI();

        if (Strings.isNotBlank(writer) && !writer.equals(user.getUserId())
                && (Pattern.matches("^/articles/[0-9]+/update$", currentURI)
                || Pattern.matches("^/articles/[0-9]+/remove$", currentURI))) {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error/403");
            requestDispatcher.forward(request, response);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws IOException, ServletException {

        String currentURI = request.getRequestURI();
        if (!Pattern.matches("^/articles/[0-9]+/form$", currentURI)) {
            return;
        }

        debug(request);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInfo");
        Article article = (Article) modelAndView.getModel().get("article");

        String writer = article.getWriter();

        if (Strings.isNotBlank(writer) && !writer.equals(user.getUserId())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error/403");
            requestDispatcher.forward(request, response);
        }
    }

    private void debug(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.debug("AuthInterceptor: {} {} {}", request.getMethod(), request.getRequestURI(), params);
    }
}
