package com.kakao.cafe.exception;


import org.springframework.http.HttpStatus;

public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 유저 아이디입니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 등록된 유저 아이디입니다."),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 질문입니다."),
    INCORRECT_USER(HttpStatus.CONFLICT, "유저 아이디 혹은 비밀번호가 일치하지 않습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 접근입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
