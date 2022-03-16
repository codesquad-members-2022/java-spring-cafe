package com.kakao.cafe.exception.user;

import org.springframework.http.HttpStatus;

public class UnMatchedPasswordException extends UserDomainException {

    public UnMatchedPasswordException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
