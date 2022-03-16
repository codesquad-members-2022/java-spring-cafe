package com.kakao.cafe.article.exception;

import org.springframework.http.HttpStatus;

public class ArticleUnsavedException extends ArticleRuntimeException {

    public ArticleUnsavedException() {
        super("글이 저장되지 않았습니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
