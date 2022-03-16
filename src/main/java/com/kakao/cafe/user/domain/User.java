package com.kakao.cafe.user.domain;

import java.time.LocalDateTime;

import com.kakao.cafe.qna.domain.Article;

public class User {
	public static final long USER_LIMIT_TIME = 10L;
	private Long id;
	private String userId;
	private String password;
	private String name;
	private String email;
	private LocalDateTime createdDate;
	private LocalDateTime lastUpdatedDate;
	private boolean restrictedEnterPassword;

	private User() {
	}

	public static User loadOf(Long id,
								String userId,
								String password,
								String name,
								String email,
								LocalDateTime createdDate,
								LocalDateTime lastUpdatedDate,
								boolean restrictedEnterPassword) {
		User user = new User();
		user.id = id;
		user.userId = userId;
		user.password = password;
		user.name = name;
		user.email = email;
		user.createdDate = createdDate;
		user.lastUpdatedDate = lastUpdatedDate;
		user.restrictedEnterPassword = restrictedEnterPassword;
		return user;
	}

	public static User createOf(String userId, String name, String email, String password) {
		User user = new User();
		user.userId = userId;
		user.name = name;
		user.email = email;
		user.password = password;
		user.createdDate = LocalDateTime.now();
		user.lastUpdatedDate = LocalDateTime.now();
		user.restrictedEnterPassword = false;
		return user;
	}

	public boolean hasId(Long id) {
		return this.id == id;
	}
	public boolean hasUserId(String userId) {
		return this.userId.equals(userId);
	}

	public boolean hasName(String name) {
		return this.name.equals(name);
	}

	public void update(UserUpdateDto.Request userDto) {
		// 입력값 제한이 있었다면, 변경시에도 검증절차가 있어야 한다.
		this.name = userDto.getName();
		this.email = userDto.getEmail();
	}

	public boolean isDifferentPassword(String password) {
		return !isTheSamePasswordAs(password);
	}

	public boolean isTheSamePasswordAs(String password) {
		return this.password.equals(password);
	}

	public void changeUserName(String name) {
		this.name = name;
	}

	public void restrictTheAttemptOfInputPassword() {
		this.restrictedEnterPassword = true;
		this.lastUpdatedDate = LocalDateTime.now();
	}

	/**
	 * 비밀번호 입력제한시에만 최종 변경시간이후의 시간을 체크한다.
	 * - 만일 사용자가 웹 사용 중지 후, 제한시간 이후 접속시 접속 가능해야 한다.
	 * - 개인정보 수정 외, 비밀번호 입력하는 로그인 페이지 접속 경우에도 비밀번호 제한이 동일하게 지속되야 한다.
	 * @return
	 */
	public boolean isAllowedStatusOfPasswordEntry() {
		if (this.restrictedEnterPassword) {
			checkPasswordEntry();
		}
		return !this.restrictedEnterPassword;
	}

	public void checkPasswordEntry() {
		if (isPassedLimitTime()) {
			this.restrictedEnterPassword = false;
		}
	}

	private boolean isPassedLimitTime() {
		return LocalDateTime.now().isAfter(this.lastUpdatedDate.plusMinutes(USER_LIMIT_TIME));
	}

	public boolean isWriter(Article article) {
		return this.id == article.getCafeUserId();
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public boolean isRestrictedEnterPassword() {
		return restrictedEnterPassword;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", userId='" + userId + '\'' +
			", password='" + password + '\'' +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			", createdDate=" + createdDate +
			", lastUpdatedDate=" + lastUpdatedDate +
			", restrictedEnterPassword=" + restrictedEnterPassword +
			'}';
	}
}
