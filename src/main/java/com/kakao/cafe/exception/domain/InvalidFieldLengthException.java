package com.kakao.cafe.exception.domain;

import com.kakao.cafe.exception.CafeRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidFieldLengthException extends CafeRuntimeException {
    public InvalidFieldLengthException() {
        super("필수 정보가 없습니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
