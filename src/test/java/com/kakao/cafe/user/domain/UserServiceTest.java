package com.kakao.cafe.user.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserDto;
import com.kakao.cafe.user.domain.UserRepository;
import com.kakao.cafe.user.domain.UserService;
import com.kakao.cafe.user.infra.MemoryUserRepository;

class UserServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void beforeEach() {
		this.userRepository = new MemoryUserRepository();
		this.userService = new UserService(userRepository);
	}

	@AfterEach
	void afterEach() {
		this.userRepository.deleteAll();
	}

	@Test
	@DisplayName("가입요청한 사용자 정보를 받으면 db에 저장하여 확인할 수 있다.")
	void register_test() {
		UserDto userDto = getUserDto();

		userService.register(userDto);
		Optional<User> actual = userRepository.findById(1L);

		assertThat(actual.isPresent()).isTrue();
	}

	@Test
	@DisplayName("가입요청한 사용자 정보중 userId와 email의 중복시 예외 발생한다.")
	void duplicated_register_test() {
		UserDto userDto = getUserDto();
		userService.register(userDto);

		assertThatThrownBy(() ->userService.register(userDto))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("없는 사용자 정보에 대한 요청에 대해 예외 발생한다.")
	void invalid_request_user_info_excpetion() {
		assertThatThrownBy(() -> userService.find(1L))
			.isInstanceOf(DomainNotFoundException.class);
	}

	private UserDto getUserDto() {
		UserDto dto = new UserDto();
		dto.setUserId("cafe");
		dto.setName("spring");
		dto.setEmail("spring@email.com");
		dto.setPassword("pwd1234");
		return dto;
	}

}
