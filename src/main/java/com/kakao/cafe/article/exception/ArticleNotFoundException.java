package com.kakao.cafe.article.exception;

import org.springframework.http.HttpStatus;

public class ArticleNotFoundException extends ArticleRuntimeException {

    public ArticleNotFoundException() {
        super("글을 찾을 수 없습니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
