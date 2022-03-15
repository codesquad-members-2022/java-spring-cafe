package com.kakao.cafe.exception.common;

import org.springframework.http.HttpStatus;

public class AccessRestrictionException extends CommonException {

    public AccessRestrictionException(HttpStatus httpStatus, String viewName, String msg) {
        super(httpStatus, viewName, msg);
    }
}
