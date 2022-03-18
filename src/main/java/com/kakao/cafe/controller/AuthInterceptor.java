package com.kakao.cafe.controller;

import com.kakao.cafe.session.SessionUser;
import java.io.IOException;
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
            return redirectLoginForm(response);
        }

        SessionUser user = (SessionUser) session.getAttribute(SessionUser.SESSION_KEY);
        if (user == null) {
            return redirectLoginForm(response);
        }
        return true;
    }

    private boolean redirectLoginForm(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login/form");
        return false;
    }

}
