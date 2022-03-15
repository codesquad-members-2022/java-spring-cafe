package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.users.Users;

public class UsersDto {
	private int userNo;
	private String userId;
	private String password;
	private String name;
	private String email;

	public UsersDto(Users user, int userNo) {
		this.userNo = userNo;
		this.userId = user.getUserId();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public int getUserNo() {
		return userNo;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
