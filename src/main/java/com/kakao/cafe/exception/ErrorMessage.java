package com.kakao.cafe.exception;

public enum ErrorMessage {
    EXISTING_NICKNAME("이미 존재하는 닉네임입니다."),
    EXISTING_EMAIL("이미 존재하는 이메일입니다."),
    NO_MATCH_USER("계정이 존재하지 않습니다."),
    NO_MATCH_ARTICLE("게시글이 존재하지 않습니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    BLANK_INPUT("공백만 입력할 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
