package com.kakao.cafe.exception.article;

import org.springframework.http.HttpStatus;

public class DuplicateArticleException extends ArticleDomainException {

    public DuplicateArticleException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
