package com.kakao.cafe.exception.repository;

import com.kakao.cafe.users.exception.UserRuntimeException;

public class RequiredFieldNotFoundException extends UserRuntimeException {
    public RequiredFieldNotFoundException(String s) {
        super(s);
    }
}
