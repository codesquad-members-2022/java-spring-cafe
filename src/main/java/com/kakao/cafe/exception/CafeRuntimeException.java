package com.kakao.cafe.exception;

import org.springframework.http.HttpStatus;

public abstract class CafeRuntimeException extends RuntimeException {

    public CafeRuntimeException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();

}
