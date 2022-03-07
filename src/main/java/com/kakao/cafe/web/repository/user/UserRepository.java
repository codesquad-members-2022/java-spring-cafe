package com.kakao.cafe.web.repository.user;

import com.kakao.cafe.web.domain.user.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

	User save(User user);

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	List<User> findAll();

	Optional<User> findByUserId(String userId);

}
