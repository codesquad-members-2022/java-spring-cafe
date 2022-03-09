package com.kakao.cafe.exception.repository;

import com.kakao.cafe.users.exception.UserRuntimeException;

public class UniqueFieldDuplicatedException extends UserRuntimeException {
    public UniqueFieldDuplicatedException(String message) {
        super(message);
    }
}
