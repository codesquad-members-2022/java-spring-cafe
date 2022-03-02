package com.kakao.cafe.web.domain.user;

public class UserDto {

	private Long id;
	private String userId;
	private String name;
	private String email;

	private UserDto(Long id, String userId, String name, String email) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public static UserDto create(User user) {
		return new UserDto(user.getId(), user.getUserId(), user.getName(), user.getEmail());
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
