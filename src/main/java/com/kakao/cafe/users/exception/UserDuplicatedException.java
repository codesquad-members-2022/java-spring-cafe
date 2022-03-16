package com.kakao.cafe.users.exception;

import org.springframework.http.HttpStatus;

public class UserDuplicatedException extends UserRuntimeException {
    public UserDuplicatedException() {
        super("이미 존재하는 회원입니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
