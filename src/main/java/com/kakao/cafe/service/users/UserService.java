package com.kakao.cafe.service.users;

import com.kakao.cafe.domain.users.Users;
import com.kakao.cafe.domain.users.MemoryUserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	MemoryUserRepository userRepository = new MemoryUserRepository();

	public void save(Users user) {
		userRepository.save(user);
	}

	public List<Users> list() {
		return userRepository.findAll();
	}
}
