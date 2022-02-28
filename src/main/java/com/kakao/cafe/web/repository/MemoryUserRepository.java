package com.kakao.cafe.web.repository;

import com.kakao.cafe.web.domain.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

	private static final Map<Long, User> store = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public User save(User user) {
		user.setId(++sequence);
		store.put(user.getId(), user);
		return user;
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return store.values().stream()
			.filter(user -> user.getEmail().equals(email))
			.findAny();
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<>(store.values());
	}
}
