package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;

@Repository
public class UserRepository {
	private static final List<User> userList = new ArrayList<>();

	public void save(User user) {
		findByUserId(user.getUserId())
			.ifPresentOrElse(u -> {
					throw new IllegalStateException(ErrorMessage.EXISTING_USER_ID.getMessage());
				},
				() -> userList.add(user));
	}

	public List<User> findAll() {
		return new ArrayList<>(List.copyOf(userList));
	}

	public Optional<User> findByUserId(String userId) {
		return userList.stream()
			.filter(user -> user.getUserId().equals(userId))
			.findAny();
	}

	public void delete(User user) {
		userList.remove(user);
	}
}
