package com.kakao.cafe.exception;

public abstract class CafeRuntimeException extends RuntimeException {


    public CafeRuntimeException(String message) {
        super(message);
    }
}
