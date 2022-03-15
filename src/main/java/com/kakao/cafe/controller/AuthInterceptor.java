package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.session.SessionUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect("/login/form");
            return false;
        }

        UserResponse user = (UserResponse) session.getAttribute(SessionUser.SESSION_KEY);
        if (user == null) {
            response.sendRedirect("/login/form");
            return false;
        }
        return true;
    }

}
