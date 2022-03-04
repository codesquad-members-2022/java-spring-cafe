package com.kakao.cafe.user.domain;

import java.util.List;

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

	public void register(UserDto.Request userDto) {
		if (userRepository.existByUserId(userDto.getUserId()) || userRepository.existByName(userDto.getName())) {
			throw new IllegalArgumentException("이미 가입한 회원 입니다.");
		}
		User user = new User(userDto.getIdNull(), userDto.getUserId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());
		userRepository.save(user);
	}

	public List<User> findUsers() {
		return userRepository.findAll();
	}

	public UserDto.Response find(Long id) {  // TODO 응답 dto 로 변경
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
}
