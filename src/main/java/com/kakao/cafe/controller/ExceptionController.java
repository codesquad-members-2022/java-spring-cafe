package com.kakao.cafe.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalStateException.class)
    public String fageNotFound(Exception e) {
        System.out.println(e.getMessage());
        return "error/404";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String loginFailed(Exception e){
        System.out.println(e.getMessage());
        return "users/login_failed";
    }
}
