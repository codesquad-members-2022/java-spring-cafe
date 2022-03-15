package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;

public class MockRepository {
	private static final List<User> userList = new ArrayList<>();

	public String save(User user) {
		findByUserId(user.getUserId())
			.ifPresentOrElse(u -> {throw new IllegalStateException(ErrorMessage.EXISTING_USER_ID.getMessage());},
				() -> userList.add(user));
		return user.getUserId();
	}

	public List<User> findAll() {
		return new ArrayList<>(List.copyOf(userList));
	}

	public Optional<User> findByUserId(String userId) {
		return userList.stream()
			.filter(user -> user.getUserId().equals(userId))
			.findAny();
	}

	public void clearList() {
		userList.clear();
	}
}
