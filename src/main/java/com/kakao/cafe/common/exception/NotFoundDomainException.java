package com.kakao.cafe.common.exception;

public class NotFoundDomainException extends RuntimeException{
	public NotFoundDomainException(String message) {
		super(message);
	}

	public NotFoundDomainException(String message, Throwable cause) {
		super(message, cause);
	}
}
