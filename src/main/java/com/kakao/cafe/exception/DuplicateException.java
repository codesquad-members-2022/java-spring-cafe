package com.kakao.cafe.exception;

public class DuplicateException extends CommonException {

    public DuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }

}
