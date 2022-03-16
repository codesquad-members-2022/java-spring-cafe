package com.kakao.cafe.exception.user;

import org.springframework.http.HttpStatus;

public class SaveUserException extends UserDomainException {

    public SaveUserException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
