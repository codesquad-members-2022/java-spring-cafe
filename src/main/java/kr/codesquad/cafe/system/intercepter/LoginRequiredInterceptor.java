package kr.codesquad.cafe.system.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session.getAttribute("currentUser") == null) {
            if ("POST".equals(request.getMethod())) {
                return true;
            }

            session.setAttribute("destinationAfterLogin", request.getRequestURI());
            response.sendRedirect("/login");

            return false;
        }

        return true;
    }
}
