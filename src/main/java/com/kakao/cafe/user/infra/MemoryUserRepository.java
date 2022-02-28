package com.kakao.cafe.user.infra;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserRepository;

@Repository
public class MemoryUserRepository implements UserRepository {
	private final Map<Long, User> data = new LinkedHashMap<>();

	@Override
	public void save(User entity) {
		if (entity.getId() != null && data.containsKey(entity.getId())) {
			data.replace(entity.getId(),
				new User(entity.getId(),
					entity.getUserId(),
					entity.getName(),
					entity.getEmail(),
					entity.getPassword()));
			return;
		}
		Long id = getNextId();
		data.put(id, entity);
		entity.setId(id);
	}

	private Long getNextId() {
		return data.size()+1L;
	}

	@Override
	public Optional<User> findById(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException("MemoryMemberRepository id");
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
}
