package com.kakao.cafe.exception.article;

import org.springframework.http.HttpStatus;

public class RemoveArticleException extends ArticleDomainException {

    public RemoveArticleException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
