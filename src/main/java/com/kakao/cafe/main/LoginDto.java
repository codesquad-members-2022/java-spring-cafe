package com.kakao.cafe.main;

public class LoginDto {
	private String userId;
	private String password;

	public LoginDto(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}
}
