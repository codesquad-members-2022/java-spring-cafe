package com.kakao.cafe.exception.common;

import org.springframework.http.HttpStatus;

public class CommonException extends Exception {

    private HttpStatus httpStatus;

    public CommonException(HttpStatus httpStatus, String msg) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
