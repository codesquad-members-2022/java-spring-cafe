package com.kakao.cafe.main;


public class SessionUser {
	private String userName;

	public static SessionUser from(String userName) {
		SessionUser sessionUser = new SessionUser();
		sessionUser.userName = userName;
		return sessionUser;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isDifferentFrom(String userId) {
		return !this.userName.equals(userId);
	}
}
