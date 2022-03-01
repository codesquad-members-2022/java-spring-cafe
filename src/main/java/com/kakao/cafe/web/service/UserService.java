package com.kakao.cafe.web.service;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Long join(User user) {
		validateDuplicateEmail(user);
		userRepository.save(user);
		return user.getId();
	}

	private void validateDuplicateEmail(User user) {
		userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		});
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findOne(Long userId) {
		return userRepository.findById(userId);
	}

}
