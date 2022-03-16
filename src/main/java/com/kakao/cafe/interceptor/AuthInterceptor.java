package com.kakao.cafe.interceptor;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInfo");

        if (user == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInfo");

        Article article = (Article) modelAndView.getModel().get("article");
        String writer = article.getWriter();
        String currentURI = request.getRequestURI();
        if (Strings.isNotBlank(writer)
                && !writer.equals(user.getUserId())
                && Pattern.matches("^/articles/[0-9]+/form$", currentURI)
                || Pattern.matches("^/articles/[0-9]+/update$", currentURI)
                || Pattern.matches("^/articles/[0-9]+/remove$", currentURI)) {

            response.sendRedirect("/error/403");
        }
    }
}
