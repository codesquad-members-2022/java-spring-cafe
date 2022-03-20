package com.kakao.cafe.interceptor;

import com.kakao.cafe.session.SessionUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {

        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }
}
