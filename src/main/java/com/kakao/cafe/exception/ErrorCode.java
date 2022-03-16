package com.kakao.cafe.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    EXISTING_NICKNAME("이미 존재하는 닉네임입니다.", CONFLICT),
    EXISTING_EMAIL("이미 존재하는 이메일입니다.", CONFLICT),
    NO_MATCH_USER("계정이 존재하지 않습니다.", NOT_FOUND),
    NO_MATCH_ARTICLE("게시글이 존재하지 않습니다.", NOT_FOUND),
    WRONG_PASSWORD("비밀번호가 틀렸습니다.", CONFLICT),
    BLANK_INPUT("공백만 입력할 수 없습니다.", BAD_REQUEST);

    public final String message;

    public final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
