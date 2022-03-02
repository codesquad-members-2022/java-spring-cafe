package com.kakao.cafe.exception;

public class NoMatchUserException extends RuntimeException{

    public NoMatchUserException(String message) {
        super(message);
    }
}
