package com.kakao.cafe.user.infra;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserRepository;

@Repository
public class MemoryUserRepository implements UserRepository {
	public static final String ERROR_OF_USER_ID = "user id";
	private final Map<Long, User> data = new LinkedHashMap<>();

	@Override
	public User save(User entity) {
		Long id = entity.getId();
		if (id != null && data.containsKey(id)) {
			data.replace(id,
				new User(id,
					entity.getUserId(),
					entity.getName(),
					entity.getEmail(),
					entity.getPassword()));
			return entity;
		}
		id = getNextId();
		data.put(id, entity);
		entity.setId(id);
		return entity;
	}

	private Long getNextId() {
		return data.size()+1L;
	}

	@Override
	public Optional<User> findById(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException(ERROR_OF_USER_ID);
		}
		if (!data.containsKey(id)) {
			throw new DomainNotFoundException(ERROR_OF_USER_ID);
		}
		User user = data.get(id);
		return Optional.of(user);
	}

	@Override
	public List<User> findAll() {
		return data.keySet().stream()
			.map(id -> data.get(id))
			.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public boolean existByUserId(String userId) {
		return this.data.keySet().stream()
			.map(id -> this.data.get(id))
			.anyMatch(user -> user.hasId(userId));
	}

	@Override
	public boolean existByName(String name) {
		return this.data.keySet().stream()
			.map(id -> this.data.get(id))
			.anyMatch(user -> user.hasName(name));
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		return Optional.empty();
	}

	@Override
	public void deleteAll() {
		this.data.clear();
	}
}
