package com.kakao.cafe.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView catchAndHandleException(RuntimeException exception) {

        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
        // 5xx
        // @ResponseStatus
    }
}
