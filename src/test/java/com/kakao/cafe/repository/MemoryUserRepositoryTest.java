package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.User;

class MemoryUserRepositoryTest {
	MemoryUserRepository userRepository = new MemoryUserRepository() ;

	@AfterEach
	void afterEach() {
		userRepository.clearList();
	}

	@Test
	@DisplayName("전체 User 수만큼 숫자를 반환한다.")
	void findUsers() {
		// given
		User user1 = new User("phil", "1234", "phil", "phil@codesquad.com");
		userRepository.save(user1);
		User user2 = new User("honux", "1234", "honux", "honux@codesquad.com");
		userRepository.save(user2);
		User user3 = new User("crong", "1234", "crong", "crong@codesquad.com");
		userRepository.save(user3);
		// when
		List<User> result = userRepository.findAll();
		// then
		assertThat(result.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("해당 Id를 찾아 반환한다.")
	void findByUserId() {
		// given
		User user1 = new User("phil", "1234", "phil", "phil@codesquad.com");
		userRepository.save(user1);
		User user2 = new User("honux", "1234", "honux", "honux@codesquad.com");
		userRepository.save(user2);
		// when
		String result = userRepository.findByUserId("phil").get().getUserId();
		// then
		assertThat(result).isEqualTo(user1.getUserId());
	}
}