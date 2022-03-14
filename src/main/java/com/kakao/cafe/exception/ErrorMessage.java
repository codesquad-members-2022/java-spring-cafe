package com.kakao.cafe.exception;

public enum ErrorMessage {
	EXISTING_USER_ID("이미 존재하는 id 입니다."),
	NO_SUCH_USER_ID("존재하지 않는 user ID 입니다."),
	DIFFERENT_PASSWORD("비밀번호가 일치하지 않습니다.");

	private String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
