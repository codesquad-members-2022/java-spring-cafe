package com.kakao.cafe.exception;

public class InternalOperationException extends CommonException {

    public InternalOperationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
