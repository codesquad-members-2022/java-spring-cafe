package com.kakao.cafe.exception;

class CommonException extends RuntimeException {

    private final ErrorCode errorCode;

    protected CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected ErrorCode getErrorCode() {
        return errorCode;
    }
}
