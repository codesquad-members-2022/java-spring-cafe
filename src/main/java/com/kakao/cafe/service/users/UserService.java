package com.kakao.cafe.service.users;

import com.kakao.cafe.domain.user.Users;
import com.kakao.cafe.domain.user.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	UserRepository userRepository = new UserRepository();

	public void save(Users user) {
		userRepository.save(user);
	}

	public List<Users> list() {
		return userRepository.findAll();
	}
}
