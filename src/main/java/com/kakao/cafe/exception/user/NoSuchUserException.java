package com.kakao.cafe.exception.user;

import java.util.NoSuchElementException;

public class NoSuchUserException extends NoSuchElementException {

    public NoSuchUserException(String msg) {
        super(msg);
    }
}
