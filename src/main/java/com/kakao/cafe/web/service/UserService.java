package com.kakao.cafe.web.service;

import com.kakao.cafe.web.domain.user.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

	void join(User user);

	List<User> findAllUsers();

	Optional<User> findById(Long userId);

	Optional<User> findByUserId(String userId);

}
