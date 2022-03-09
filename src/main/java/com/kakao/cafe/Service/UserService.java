package com.kakao.cafe.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.DuplicateMessage;
import com.kakao.cafe.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/*
	* 회원가입
	* */
	public String join(User user) {
		validateDuplicateUser(user);
		userRepository.save(user);
		return user.getUserId();
	}

	/*
	* userId 중복확인
	* */
	private void validateDuplicateUser(User user){
		userRepository.findByUserId(user.getUserId())
			.ifPresent(u -> {
				throw new IllegalStateException(DuplicateMessage.DUPLICATED_USER_ID.getErrorMessage());
				});
	}

	/*
	* 전체 회원 조회
	* */
	public List<User> findUsers() {
		return userRepository.findAll();
	}

	/*
	userId를 통해 특정 회원 조회
	* */
	public Optional<User> findOne(String userId) {
		return userRepository.findByUserId(userId);
	}
}
