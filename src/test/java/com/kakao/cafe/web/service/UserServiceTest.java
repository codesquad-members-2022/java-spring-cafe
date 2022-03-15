package com.kakao.cafe.web.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.repository.user.MemoryUserRepository;
import com.kakao.cafe.web.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

	UserService userService;
	UserRepository repository;

	@BeforeEach
	void setup() {
		repository = new MemoryUserRepository();
		User user = new User("jeremy0405", "qwer1234", "Jerry", "jeremy0405@naver.com");
		repository.save(user);
		userService = new UserService(repository);
	}

	@Test
	@DisplayName("이미 존재하는 아이디로 회원가입을 한다면 IllegalStateException이 발생한다.")
	void joinDuplicateUserId() {
	    //given
		User user1 = new User("jeremy0405", "qwer1234", "장형석", "abc@naver.com");

	    //when
		Exception exception = assertThrows(IllegalStateException.class,	() -> userService.join(user1));
		String actualMessage = exception.getMessage();
		String expectedMessage = "이미 존재하는 아이디입니다.";

		//then
		assertThat(actualMessage).isEqualTo(expectedMessage);
	}

	@Test
	@DisplayName("이미 존재하는 email로 회원가입을 한다면 IllegalStateException이 발생한다.")
	void joinDuplicateEmail() {
	    //given
		User user1 = new User("jang2316", "qwer1234", "장형석", "jeremy0405@naver.com");

	    //when
		Exception exception = assertThrows(IllegalStateException.class,	() -> userService.join(user1));
		String actualMessage = exception.getMessage();
		String expectedMessage = "이미 존재하는 이메일입니다.";

	    //then
		assertThat(actualMessage).isEqualTo(expectedMessage);
	}

}
