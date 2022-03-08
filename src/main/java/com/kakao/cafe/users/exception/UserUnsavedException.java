package com.kakao.cafe.users.exception;

public class UserUnsavedException extends UserRuntimeException {

    public UserUnsavedException() {
        super("회원 저장하지 못했습니다.");
    }
}
