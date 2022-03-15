package com.kakao.cafe.exception.user;

import org.springframework.http.HttpStatus;

public class DuplicateUserException extends UserDomainException {

    public DuplicateUserException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
