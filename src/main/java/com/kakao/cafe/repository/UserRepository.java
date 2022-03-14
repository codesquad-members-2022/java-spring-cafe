package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;

@Repository
public class UserRepository {
	private static final String ERROR_MESSAGE_DUPLICATED_USER_ID = "이미 존재하는 id 입니다.";
	private static final List<User> userList = new ArrayList<>();

	public void save(User user) {
		findByUserId(user.getUserId())
			.ifPresentOrElse(u -> {throw new IllegalStateException(ERROR_MESSAGE_DUPLICATED_USER_ID);},
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
}
