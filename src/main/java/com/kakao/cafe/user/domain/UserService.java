package com.kakao.cafe.user.domain;

import static com.kakao.cafe.user.domain.UserUpdateDto.*;

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

	public long register(UserDto.Request userDto) {
		if (userRepository.existByUserId(userDto.getUserId())) {
			throw new IllegalArgumentException("이미 가입한 회원 입니다.");
		}
		User user = new User(userDto.getUserId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());
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

	public UserUpdateDto.Response findUserForUpdateFrom(String userId) {
		User user = getUserByUserId(userId);
		UserUpdateDto.Response response = new UserUpdateDto.Response(user);
		if (isValidPasswordEntry(user)) {
			return response;
		}
		response.setMessage(USER_MESSAGE_OF_EXCEED_PASSWORD_ENTRY);
		return response;
	}

	private boolean isValidPasswordEntry(User user) {
		if (user.isAllowedStatusOfPasswordEntry()) {
			userRepository.save(user);
			return true;
		}
		return false;
	}

	private User getUserByUserId(String userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> {
				throw new DomainNotFoundException("user");
			});
	}

	public boolean isValidPassword(UserUpdateDto.Request userDto) {
		User user = get(userDto.getIdByLong());
		return user.isTheSamePasswordAs(userDto.getPassword());
	}

	public void restrictPasswordChange(String userId) {
		User user = getUserByUserId(userId);
		user.restrictTheAttemptOfInputPassword();
		userRepository.save(user);
	}
}
