package com.kakao.cafe.exception;


import org.springframework.http.HttpStatus;

public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 유저 아이디입니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 등록된 유저 아이디입니다."),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 질문입니다."),
    INCORRECT_USER(HttpStatus.CONFLICT, "유저 아이디 혹은 비밀번호가 일치하지 않습니다."),
    INTERNAL_ERROR(HttpStatus.BAD_REQUEST, "서버 내부에서 오류가 발생했습니다."),
    SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 로그인 정보입니다."),
    FIELD_NOT_FOUND(HttpStatus.NOT_FOUND, "필수적인 필드 정보가 존재하지 않습니다.");

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
