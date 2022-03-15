package com.kakao.cafe.exception.article;

import com.kakao.cafe.exception.user.UserDomainException;
import org.springframework.http.HttpStatus;

public class DuplicateArticleException extends UserDomainException {

    public DuplicateArticleException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }
}
