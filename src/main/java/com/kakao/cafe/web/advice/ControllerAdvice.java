package com.kakao.cafe.web.advice;

import com.kakao.cafe.exception.UnAuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(UnAuthorizationException.class)
    public String authorizationHandler(UnAuthorizationException e, Model model) {
        log.error("UN_AUTHORIZATION_HANDLER {}", e.getMessage());
        model.addAttribute("errormessage", e.getMessage());
        return "/error/authorization";
    }

}
