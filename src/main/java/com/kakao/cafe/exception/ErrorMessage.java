package com.kakao.cafe.exception;

public enum ErrorMessage {
    EXISTING_EMAIL("이미 존재하는 이메일입니다."),
    EXISTING_NICKNAME("이미 존재하는 닉네임입니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
