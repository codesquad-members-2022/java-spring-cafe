package com.kakao.cafe.web.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.repository.user.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

	@Autowired UserService userService;
	@Autowired UserRepository userRepository;

	@Test
	@DisplayName("이미 존재하는 아이디로 회원가입을 한다면 IllegalStateException이 발생한다.")
	void joinDuplicateUserId() {
		//given
		String uuid = UUID.randomUUID().toString();
		User user1 = new User(uuid, "qwer1234", "장형석", uuid + "@naver.com");
		User user2 = new User(uuid, "qwer1234", "Jerry", uuid + "a@naver.com");

		//when
		userService.join(user1);
		Exception exception = assertThrows(IllegalStateException.class,	() -> userService.join(user2));
		String actualMessage = exception.getMessage();
		String expectedMessage = "이미 존재하는 아이디입니다.";

		//then
		assertThat(actualMessage).isEqualTo(expectedMessage);
	}

	@Test
	@DisplayName("이미 존재하는 email로 회원가입을 한다면 IllegalStateException이 발생한다.")
	void joinDuplicateEmail() {
		//given
		String uuid = UUID.randomUUID().toString();
		User user1 = new User(uuid, "qwer1234", "장형석", uuid + "@naver.com");
		User user2 = new User(uuid + "a", "qwer1234", "Jerry", uuid + "@naver.com");

		//when
		userService.join(user1);
		Exception exception = assertThrows(IllegalStateException.class,	() -> userService.join(user2));
		String actualMessage = exception.getMessage();
		String expectedMessage = "이미 존재하는 이메일입니다.";

		//then
		assertThat(actualMessage).isEqualTo(expectedMessage);
	}

}
