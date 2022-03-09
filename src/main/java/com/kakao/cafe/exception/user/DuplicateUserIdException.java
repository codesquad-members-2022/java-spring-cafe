package com.kakao.cafe.exception.user;

public class DuplicateUserIdException extends IllegalArgumentException {

    public DuplicateUserIdException(String msg) {
        super(msg);
    }
}
