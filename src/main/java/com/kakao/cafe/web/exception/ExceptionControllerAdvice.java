package com.kakao.cafe.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NoSuchElementException.class)
    public String get(HttpServletRequest request, HttpServletResponse response) {
        logger.info(request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "error-pages/404";
    }

    @ExceptionHandler(Exception.class)
    public String getExceptException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        logger.info(request.getRequestURI());
        logger.info("ErrorMessage= {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return "error-pages/500";
    }
}

