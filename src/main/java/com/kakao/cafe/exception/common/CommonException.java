package com.kakao.cafe.exception.common;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {

    private HttpStatus httpStatus;
    private String viewName;

    public CommonException(HttpStatus httpStatus, String viewName, String msg) {
        super(msg);
        this.viewName = viewName;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getViewName() {
        return viewName;
    }
}
