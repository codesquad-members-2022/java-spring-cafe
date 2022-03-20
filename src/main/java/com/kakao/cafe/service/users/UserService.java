package com.kakao.cafe.service.users;

import com.kakao.cafe.domain.users.Users;
import com.kakao.cafe.domain.users.MemoryUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private MemoryUserRepository userRepository;

	public UserService(MemoryUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void save(Users user) {
		userRepository.save(user);
	}

	public List<Users> list() {
		return userRepository.findAll();
	}

	public Users one(String userId) {
		return userRepository.findByUserId(userId);
	}
}
