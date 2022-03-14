package com.kakao.cafe.article.exception;

import com.kakao.cafe.exception.CafeRuntimeException;

public abstract class ArticleRuntimeException extends CafeRuntimeException {

    public ArticleRuntimeException(String message) {
        super(message);
    }
}
