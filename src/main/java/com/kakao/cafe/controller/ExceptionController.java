package com.kakao.cafe.controller;

import com.kakao.cafe.exception.UserIncorrectAccessException;
import com.kakao.cafe.exception.LoginFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserIncorrectAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleDuplicateUserException(UserIncorrectAccessException exception) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleLoginFailException(LoginFailedException exception) {
        ModelAndView modelAndView = new ModelAndView("error/login_failed");
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }
}
