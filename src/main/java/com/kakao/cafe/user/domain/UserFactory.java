package com.kakao.cafe.user.domain;

import java.time.LocalDateTime;

public class UserFactory {
	public static User create(Long id,
							String userId,
							String password,
							String name,
							String email,
							LocalDateTime createdDate,
							LocalDateTime lastUpdatedDate,
							boolean restrictedEnterPassword) {
		return User.loadOf(id, userId, password, name, email, createdDate, lastUpdatedDate, restrictedEnterPassword);
	}

	public static User create(String userId, String name, String email, String password) {
		return User.createOf(userId, name, email, password);
	}
}
