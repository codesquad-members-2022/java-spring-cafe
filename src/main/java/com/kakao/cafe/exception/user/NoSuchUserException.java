package com.kakao.cafe.exception.user;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends UserDomainException {

    public NoSuchUserException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
