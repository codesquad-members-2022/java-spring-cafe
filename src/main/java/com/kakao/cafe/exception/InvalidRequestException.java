package com.kakao.cafe.exception;

public class InvalidRequestException extends CommonException {

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
