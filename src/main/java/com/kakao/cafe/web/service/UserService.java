package com.kakao.cafe.web.service;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void join(User user) {
		validateDuplicateEmail(user);
		validateDuplicateUserId(user);
		userRepository.save(user);
	}

	private void validateDuplicateEmail(User user) {
		userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		});
	}

	private void validateDuplicateUserId(User user) {
		userRepository.findByUserId(user.getUserId()).ifPresent(m -> {
			throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
		});
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}


}
