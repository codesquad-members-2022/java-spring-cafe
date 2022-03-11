package com.kakao.cafe.exception;

public class NotFoundException extends CommonException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
