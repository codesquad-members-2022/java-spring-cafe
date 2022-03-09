package com.kakao.cafe.exception;

public enum DuplicateMessage {
	DUPLICATED_USER_ID("이미 존재하는 id 입니다.");

	private final String errorMessage;

	DuplicateMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
