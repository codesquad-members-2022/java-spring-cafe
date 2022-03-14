package com.kakao.cafe.domain;

import com.kakao.cafe.exception.ErrorMessage;

public class User {
	private String userId;
	private String password;
	private String name;
	private String email;

	public User(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void updateProfile(String password, String name, String email) {
		if (!password.equals(this.password)) {
			throw new IllegalStateException(ErrorMessage.DIFFERENT_PASSWORD.getMessage());
		}
		this.name = name;
		this.email = email;
	}
}
