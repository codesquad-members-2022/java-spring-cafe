package com.kakao.cafe.exception;

public class UnAuthorizationException extends RuntimeException {
    public UnAuthorizationException(String message) {
        super(message);
    }
}
