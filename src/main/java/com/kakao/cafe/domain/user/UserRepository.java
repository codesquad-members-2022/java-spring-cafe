package com.kakao.cafe.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepository {

	Map<String, Users> users = new HashMap<>();

	public List<Users> findAll() {
		Collection<Users> values = users.values();
		return (List<Users>) values;
	}

	public void save(Users user) {
		users.put(user.getUserId(), user);
	}

	public Optional<Users> findByUserId(String userId) {
		return Optional.ofNullable(users.get(userId));
	}
}
