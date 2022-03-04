package com.kakao.cafe.users.exception;

public class UserDuplicatedException extends UserRuntimeException {
    public UserDuplicatedException(String message) {
        super(message);
    }
}
