package com.kakao.cafe.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {InvalidRequestException.class, DuplicateException.class,
        NotFoundException.class, InternalOperationException.class})
    public ModelAndView handleCustomException(HttpServletRequest request,
        CommonException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ModelAndView mav = new ModelAndView("error/index");
        mav.addObject("url", request.getRequestURL());
        mav.addObject("status", errorCode.getHttpStatus());
        mav.addObject("message", errorCode.getMessage());

        return mav;
    }
}
