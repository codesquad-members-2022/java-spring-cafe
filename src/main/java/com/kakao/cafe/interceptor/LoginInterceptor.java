package com.kakao.cafe.interceptor;

import com.kakao.cafe.constants.LoginConstants;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(LoginConstants.SESSIONED_USER) == null) {
            response.sendRedirect("/user/login?RedirectURL="+requestURI);
            return false;
        }

        return true;
    }
}
