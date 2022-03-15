package com.kakao.cafe.web.domain.user;

public class User {

	private Long id;
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

	public boolean isEqualEmail(String email) {
		return this.email.equals(email);
	}

	public boolean isEqualUserId(String userId) {
		return this.userId.equals(userId);
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", userId='" + userId + '\'' +
			", password='" + password + '\'' +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			'}';
	}

}
