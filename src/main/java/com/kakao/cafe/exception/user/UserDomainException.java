package com.kakao.cafe.exception.user;

import org.springframework.http.HttpStatus;

public class UserDomainException extends RuntimeException {

    private HttpStatus httpStatus;

    public UserDomainException(HttpStatus httpStatus, String msg) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
