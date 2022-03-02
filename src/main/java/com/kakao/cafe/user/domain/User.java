package com.kakao.cafe.user.domain;

public class User {
	private Long id;
	private String userId;
	private String password;
	private String name;
	private String email;

	public User(Long id, String userId, String name, String email, String password) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public boolean hasId(String userId) {
		return this.userId.equals(userId);
	}

	public boolean hasName(String name) {
		return this.name.equals(name);
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

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "User{" +
			"userId='" + userId + '\'' +
			", password='" + password + '\'' +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
