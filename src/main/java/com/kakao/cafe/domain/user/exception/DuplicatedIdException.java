package com.kakao.cafe.domain.user.exception;

public class DuplicatedIdException extends RuntimeException {

    private static final String MESSAGE = "[ERROR] 존재하는 ID입니다. 다시 입력하세요";
    public DuplicatedIdException() {
        super(MESSAGE);
    }
}
