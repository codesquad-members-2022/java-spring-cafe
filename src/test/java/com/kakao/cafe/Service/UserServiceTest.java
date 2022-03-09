package com.kakao.cafe.Service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;

class UserServiceTest {
	MemoryUserRepository userRepository;
	UserService userService;

	@BeforeEach
	void beforeEach() {
		userRepository = new MemoryUserRepository();
		userService = new UserService(userRepository);
	}

	@AfterEach
	void afterEach() {
		userRepository.clearList();
	}
	@Test
	@DisplayName("회원가입한 User의 id를 반환한다.")
	void test() {
		// given
		User user = new User("phil", "1234", "phil", "phil@codesquad.com");
		// when
		String id = userService.join(user);
		// then
		assertThat(id).isEqualTo("phil");
	}

	@Test
	@DisplayName("전체 User 수만큼 숫자를 반환한다.")
	void findUsers() {
		// given
		User user1 = new User("phil", "1234", "phil", "phil@codesquad.com");
		userService.join(user1);
		User user2 = new User("honux", "1234", "honux", "honux@codesquad.com");
		userService.join(user2);
		User user3 = new User("crong", "1234", "crong", "crong@codesquad.com");
		userService.join(user3);
		// when
		List<User> result = userService.findUsers();
		// then
		assertThat(result.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("해당 Id의 유저를 찾아 반환한다.")
	void findByUserId() {
		// given
		User user1 = new User("phil", "1234", "phil", "phil@codesquad.com");
		userService.join(user1);
		User user2 = new User("honux", "1234", "honux", "honux@codesquad.com");
		userService.join(user2);
		User user3 = new User("crong", "1234", "crong", "crong@codesquad.com");
		userService.join(user3);
		// when
		User result = userService.findOne("honux").get();
		// then
		assertThat(result).isEqualTo(user2);
	}

	@Test
	@DisplayName("중복 id로 회원가입이 되면 에러가 발생한다.")
	void duplicateJoin() {
		// given
		User user1 = new User("phil", "1234", "phil", "phil@codesquad.com");
		userService.join(user1);
		User user2 = new User("phil", "1234", "phil2", "phil2@codesquad.com");
		// then
		Assertions.assertThrows(IllegalStateException.class, () -> userService.join(user2));
	}
}