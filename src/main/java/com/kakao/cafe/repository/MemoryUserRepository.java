package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;

@Repository
public class MemoryUserRepository implements UserRepository {

	private static final List<User> userList = new ArrayList<>();

	@Override
	public void save(User user) {
		userList.add(user);
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<>(List.copyOf(userList));
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		return userList.stream()
			.filter(user -> user.getUserId().equals(userId))
			.findAny();
	}
}
