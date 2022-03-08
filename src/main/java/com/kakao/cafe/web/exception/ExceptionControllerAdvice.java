package com.kakao.cafe.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NoSuchElementException.class)
    public String get(HttpServletRequest request) {
        logger.info(request.getRequestURI());
        return "error-pages/404";
    }

    @ExceptionHandler(Exception.class)
    public String getExceptException(Exception e, HttpServletRequest request) {
        logger.info(request.getRequestURI());
        logger.info("ErrorMessage= {}", e.getMessage());
        return "error-pages/500";
    }
}
