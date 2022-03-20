package com.kakao.cafe.exception.article;

import org.springframework.http.HttpStatus;

public class SaveArticleException extends ArticleDomainException {

    public SaveArticleException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
