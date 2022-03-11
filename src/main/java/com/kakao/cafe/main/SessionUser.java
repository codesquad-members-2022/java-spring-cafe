package com.kakao.cafe.main;

import java.time.LocalDate;

public class SessionUser {
	private String sessionId;
	private String userName;
	private String createDate;
	private String expireDate;
	private String lastAccessData;

	public SessionUser(String sessionId, String userName) {
		this.sessionId = sessionId;
		this.userName = userName;
		this.createDate = LocalDate.now().toString();
		this.expireDate = LocalDate.now().plusMonths(1L).toString();
		this.lastAccessData = this.createDate;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public String getLastAccessData() {
		return lastAccessData;
	}
}
