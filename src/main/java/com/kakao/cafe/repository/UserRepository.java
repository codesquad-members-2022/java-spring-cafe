package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public interface UserRepository {

	void save(User user);
	Optional<User> findByUserId(String userId);
	List<User> findAll();
}
