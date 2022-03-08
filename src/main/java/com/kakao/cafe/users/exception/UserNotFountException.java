package com.kakao.cafe.users.exception;

public class UserNotFountException extends UserRuntimeException {

    public UserNotFountException() {
        super("회원을 찾을 수 없습니다.");
    }
}
