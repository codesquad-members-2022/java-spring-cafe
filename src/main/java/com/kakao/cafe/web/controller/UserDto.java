package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.user.User;

public class UserDto {

	private Long id;
	private String userId;
	private String name;
	private String email;

	public UserDto(User user) {
		this.id = user.getId();
		this.userId = user.getUserId();
		this.name = user.getName();
		this.email = user.getEmail();
	}

}
