package com.kakao.cafe.web.repository.user;

import com.kakao.cafe.web.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

	private static final Map<Long, User> store = new ConcurrentHashMap<>();
	private static final AtomicLong sequence = new AtomicLong();

	@Override
	public User save(User user) {
		user.setId(sequence.incrementAndGet());
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
			.filter(user -> user.isEqualEmail(email))
			.findAny();
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		return store.values().stream()
			.filter(user -> user.isEqualUserId(userId))
			.findAny();
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<>(store.values());
	}

	public void clear() {
		store.clear();
	}

}
