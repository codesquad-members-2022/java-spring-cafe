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

	public void register(UserDto userDto) {
		if (userRepository.existByUserId(userDto.getUserId()) || userRepository.existByName(userDto.getName())) {
			throw new IllegalArgumentException("이미 가입한 회원 입니다.");
		}
		User user = new User(userDto.getIdByLong(), userDto.getUserId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());
		userRepository.save(user);
	}

	public List<User> findUsers() {
		return userRepository.findAll();
	}

	public User find(Long id) {  // TODO 응답 dto 로 변경
		return getUser(id);
	}

	private User getUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> {
				throw new DomainNotFoundException("user");
			});
	}

	public void changeProfile(UserDto userDto) {
		User user = getUser(userDto.getIdByLong());
		user.update(userDto);
		userRepository.save(user);
	}
}
