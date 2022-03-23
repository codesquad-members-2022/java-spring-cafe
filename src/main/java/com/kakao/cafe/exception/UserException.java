package com.kakao.cafe.exception;

public class UserException extends RuntimeException {
    public final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }
}
