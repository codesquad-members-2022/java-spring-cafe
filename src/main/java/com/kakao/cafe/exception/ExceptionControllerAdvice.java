package com.kakao.cafe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);


    @ExceptionHandler(UserException.class)
    public String handleException(Model model, UserException exception) {
        logger.info("exception handling");
        model.addAttribute("status", exception.getHttpStatus().toString());
        model.addAttribute("message", exception.getMessage());

        return "/error-page/400";

    }

}
