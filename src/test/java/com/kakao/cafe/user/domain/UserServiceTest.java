package com.kakao.cafe.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.practice.DbText;
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserDto;
import com.kakao.cafe.user.domain.UserRepository;
import com.kakao.cafe.user.domain.UserService;
import com.kakao.cafe.user.infra.MemoryUserRepository;

@ExtendWith(MockitoExtension.class)
public
class UserServiceTest {
	public static final String CAFE_USER_ID = "cafe";
	public static final String CAFE_USER_NAME = "spring";
	public static final String CAFE_USER_EMAIL = "spring@email.com";
	public static final String CAFE_USER_PASSWORD = "pwd1234";
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private UserDto.Request userDto = getUserDto();

	@Test
	@DisplayName("가입요청한 사용자 정보를 받으면 db에 저장하여 확인할 수 있다.")
	void register_test() {
		User expected = new User(CAFE_USER_ID, CAFE_USER_NAME, CAFE_USER_EMAIL, CAFE_USER_PASSWORD);
		expected.setId(1L);
		when(userRepository.save(any()))
			.thenReturn(expected);

		long actual = userService.register(userDto);

		assertThat(actual).isEqualTo(expected.getId());
	}

	@Test
	@DisplayName("가입요청한 사용자 정보중 userId와 email의 중복시 예외 발생한다.")
	void duplicated_register_test() {
		when(userRepository.existByUserId(userDto.getUserId()))
			.thenReturn(true);

		assertThatThrownBy(() ->userService.register(userDto))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("없는 사용자 정보에 대한 요청에 대해 예외 발생한다.")
	void invalid_request_user_info_excpetion() {
		assertThatThrownBy(() -> userService.find(1L))
			.isInstanceOf(DomainNotFoundException.class);
	}

	private UserDto.Request getUserDto() {
		UserDto.Request dto = new UserDto.Request(CAFE_USER_ID, CAFE_USER_PASSWORD, CAFE_USER_NAME, CAFE_USER_EMAIL);
		return dto;
	}
}
