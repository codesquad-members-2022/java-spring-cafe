package com.kakao.cafe.exception;

public class ArticleException extends RuntimeException {
    public final ErrorCode errorCode;

    public ArticleException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }

}
