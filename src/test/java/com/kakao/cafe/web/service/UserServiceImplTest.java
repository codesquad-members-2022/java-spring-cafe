package com.kakao.cafe.web.service;

import static org.junit.jupiter.api.Assertions.*;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.repository.MemoryUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {

	static UserService userService;
	static MemoryUserRepository repository;

	@BeforeAll
	static void setup() {
		repository = new MemoryUserRepository();
		User user = new User();
		user.setUserId("jeremy0405");
		user.setPassword("qwer1234");
		user.setName("Jerry");
		user.setEmail("jeremy0405@naver.com");
		repository.save(user);
		userService = new UserService(repository);
	}

	@Test
	@DisplayName("이미 존재하는 아이디로 회원가입을 한다면 IllegalArgumentException이 발생한다.")
	void joinDuplicateUserId() {
	    //given
		User user1 = new User();
		user1.setUserId("jeremy0405");
		user1.setPassword("qwer1234");
		user1.setName("장형석");
		user1.setEmail("hsjang0405@gmail.com");

	    //when

	    //then
		assertThrows(IllegalArgumentException.class, () -> userService.join(user1));
	}

	@Test
	@DisplayName("이미 존재하는 email로 회원가입을 한다면 IllegalArgumentException이 발생한다.")
	void joinDuplicateEmail() {
	    //given
		User user1 = new User();
		user1.setUserId("jang2316");
		user1.setPassword("qwer1234");
		user1.setName("장형석");
		user1.setEmail("jeremy0405@naver.com");

	    //when

	    //then
		assertThrows(IllegalArgumentException.class, () -> userService.join(user1));

	}


}