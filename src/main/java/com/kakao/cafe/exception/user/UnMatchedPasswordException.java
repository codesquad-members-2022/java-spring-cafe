package com.kakao.cafe.exception.user;

public class UnMatchedPasswordException extends IllegalStateException {

    public UnMatchedPasswordException(String msg) {
        super(msg);
    }
}
