package com.kakao.cafe.user.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.cafe.common.exception.NotFoundDomainException;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void register(UserDto userDto) {
		User user = new User(userDto.getId(), userDto.getUserId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());
		if (userRepository.existByUserId(userDto.getUserId()) || userRepository.existByName(userDto.getName())) {
			throw new IllegalArgumentException("이미 가입한 회원 입니다.");
		}
		userRepository.save(user);
	}

	public List<User> findUsers() {
		return userRepository.findAll();
	}

	public User findUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> {
				throw new NotFoundDomainException("user");
			});
	}
}
