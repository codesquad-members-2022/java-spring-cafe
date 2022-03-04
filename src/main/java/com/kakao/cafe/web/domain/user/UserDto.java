package com.kakao.cafe.web.domain.user;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
