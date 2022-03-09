package com.kakao.cafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CafeRuntimeException.class)
    public ModelAndView cafeRuntimeException(CafeRuntimeException e) {
        ModelAndView modelAndView = new ModelAndView();

        switch (e.getStatus()) {
            case BAD_REQUEST: modelAndView.setViewName("error/400"); break;
            case NOT_FOUND: modelAndView.setViewName("error/404"); break;
            case INTERNAL_SERVER_ERROR:
            default:
                modelAndView.setViewName("error/500"); break;
        }

        modelAndView.setStatus(e.getStatus());

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String exception() {
        return "error/500";
    }
}
