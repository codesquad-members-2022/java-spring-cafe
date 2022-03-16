package com.kakao.cafe.exception;

public class UserIncorrectAccessException extends RuntimeException {

    public UserIncorrectAccessException(String message) {
        super(message);
    }
}
