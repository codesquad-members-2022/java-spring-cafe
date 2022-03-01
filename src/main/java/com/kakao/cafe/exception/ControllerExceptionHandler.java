package com.kakao.cafe.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(HttpServletRequest request, CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ModelAndView mav = new ModelAndView("error/index");
        mav.addObject("url", request.getRequestURL());
        mav.addObject("status", errorCode.getHttpStatus());
        mav.addObject("message", errorCode.getMessage());

        return mav;
    }
}
