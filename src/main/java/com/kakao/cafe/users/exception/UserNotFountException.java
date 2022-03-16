package com.kakao.cafe.users.exception;

import org.springframework.http.HttpStatus;

public class UserNotFountException extends UserRuntimeException {
    public UserNotFountException() {
        super("회원을 찾을 수 없습니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
