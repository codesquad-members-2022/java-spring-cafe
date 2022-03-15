package com.kakao.cafe.exception.article;

import org.springframework.http.HttpStatus;

public class ArticleDomainException extends RuntimeException {

    private HttpStatus httpStatus;

    public ArticleDomainException(HttpStatus httpStatus, String msg) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
