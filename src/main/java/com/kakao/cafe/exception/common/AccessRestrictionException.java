package com.kakao.cafe.exception.common;

import org.springframework.http.HttpStatus;

public class AccessRestrictionException extends CommonException {

    public AccessRestrictionException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
