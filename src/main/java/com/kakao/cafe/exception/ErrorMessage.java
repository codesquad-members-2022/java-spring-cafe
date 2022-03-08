package com.kakao.cafe.exception;

public enum ErrorMessage {
    EXISTING_NICKNAME("이미 존재하는 닉네임입니다."),
    NO_MATCH("조건에 맞는 계정이 존재하지 않습니다");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
