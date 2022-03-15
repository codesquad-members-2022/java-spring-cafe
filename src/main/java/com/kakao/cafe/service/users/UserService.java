package com.kakao.cafe.service.users;

import com.kakao.cafe.controller.dto.UsersDto;
import com.kakao.cafe.domain.users.Users;
import com.kakao.cafe.domain.users.MemoryUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	MemoryUserRepository userRepository = new MemoryUserRepository();

	public void save(Users user) {
		userRepository.save(user);
	}

	public List<UsersDto> list() {
		List<UsersDto> usersDtoList = new ArrayList<>();

		List<Users> usersList = userRepository.findAll();
		for (int idx = 0; idx < usersList.size(); idx++) {
			usersDtoList.add(new UsersDto(usersList.get(idx), idx+1));
		}
		return usersDtoList;
	}

	public Users one(String userId) {
		return userRepository.findByUserId(userId);
	}
}
