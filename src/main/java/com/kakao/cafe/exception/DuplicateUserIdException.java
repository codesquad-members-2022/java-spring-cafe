package com.kakao.cafe.exception;

public class DuplicateUserIdException extends RuntimeException{

    public DuplicateUserIdException(String message) {
        super(message);
    }
}
