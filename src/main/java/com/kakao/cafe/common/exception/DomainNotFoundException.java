package com.kakao.cafe.common.exception;

public class DomainNotFoundException extends RuntimeException{
	public DomainNotFoundException(String message) {
		super(message);
	}

	public DomainNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
