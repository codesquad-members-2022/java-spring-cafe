package com.kakao.cafe.interceptor;

import com.kakao.cafe.constants.LoginConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        logger.info("[{}] LoginInterceptor checking session... ", requestURI);
        if (session == null || session.getAttribute(LoginConstants.SESSIONED_USER) == null) {
            logger.info("[{}]'s has not session or sessionedUser ", requestURI);
            response.sendRedirect("/user/login");
            return false;
        }

        return true;
    }
}
