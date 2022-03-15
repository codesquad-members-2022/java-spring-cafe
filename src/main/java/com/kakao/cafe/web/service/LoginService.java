package com.kakao.cafe.web.service;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	private final UserRepository userRepository;

	public LoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User login(String userId, String password) {
		return userRepository.findByUserId(userId)
			.filter(user -> user.isEqualPassword(password))
			.orElse(null);
	}

}
