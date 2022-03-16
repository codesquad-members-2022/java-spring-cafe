package com.kakao.cafe.interceptor;

import com.kakao.cafe.domain.User;
import org.apache.logging.log4j.util.Strings;
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
        User user = (User) session.getAttribute("userInfo");

        if (user == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }
}
