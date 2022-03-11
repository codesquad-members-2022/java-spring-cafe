package com.kakao.cafe.user.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.cafe.common.exception.DomainNotFoundException;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public long register(UserDto.Request userDto) {
		if (userRepository.existByUserId(userDto.getUserId()) || userRepository.existByName(userDto.getName())) {
			throw new IllegalArgumentException("이미 가입한 회원 입니다.");
		}
		User user = new User(userDto.getIdNull(), userDto.getUserId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());
		User getUser = userRepository.save(user);
		return getUser.getId();
	}

	public List<User> findUsers() {
		return userRepository.findAll();
	}

	public UserDto.Response find(Long id) {
		User user = get(id);
		return new UserDto.Response(user);
	}

	private User get(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> {
				throw new DomainNotFoundException("user");
			});
	}

	public void changeProfile(UserUpdateDto.Request userDto) {
		User user = get(userDto.getIdByLong());
		user.update(userDto);
		userRepository.save(user);
	}

	public UserDto.Response findUserId(String userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> {
				throw new DomainNotFoundException("user");
			});
		return new UserDto.Response(user);
	}
}
