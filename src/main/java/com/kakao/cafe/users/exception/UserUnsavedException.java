package com.kakao.cafe.users.exception;

import org.springframework.http.HttpStatus;

public class UserUnsavedException extends UserRuntimeException {
    public UserUnsavedException() {
        super("회원 저장하지 못했습니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
