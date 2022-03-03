package com.kakao.cafe.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        MethodParameter[] params = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            params = hm.getMethodParameters();
        }
        log.info("preHandle [{}] [{}] {}", method, requestURI, params);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {

        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        log.info("afterCompletion [{}] [{}]", method, requestURI);
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
