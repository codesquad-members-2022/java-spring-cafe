package com.kakao.cafe.exception.article;

import org.springframework.http.HttpStatus;

public class NoSuchArticleException extends ArticleDomainException {

    public NoSuchArticleException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
