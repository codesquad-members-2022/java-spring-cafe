package com.kakao.cafe.users.exception;

import com.kakao.cafe.exception.CafeRuntimeException;

public abstract class UserRuntimeException extends CafeRuntimeException {
    public UserRuntimeException(String message) {
        super(message);
    }
}
