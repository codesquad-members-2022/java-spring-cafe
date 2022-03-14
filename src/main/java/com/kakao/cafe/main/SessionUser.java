package com.kakao.cafe.main;


public class SessionUser {
	public static final String SESSION_KEY = "sessionedUser";
	private String userId;
	private String userName;
	private boolean isAuthenticated;

	private SessionUser() {
	}

	public SessionUser(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	// 검증된 사용자의 경우 생성자
	public static SessionUser of(String userId, String userName) {
		SessionUser sessionUser = new SessionUser();
		sessionUser.userId = userId;
		sessionUser.userName = userName;
		sessionUser.isAuthenticated = true;
		return sessionUser;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isDifferentFrom(String userId) {
		return !this.userName.equals(userId);
	}

	public boolean isValidated() {
		return this.isAuthenticated;
	}
}
