package com.kakao.cafe.exception.repository;

import com.kakao.cafe.exception.CafeRuntimeException;
import org.springframework.http.HttpStatus;

public class UniqueFieldDuplicatedException extends CafeRuntimeException {
    public UniqueFieldDuplicatedException() {
        super("중복된 필드가 존재합니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
