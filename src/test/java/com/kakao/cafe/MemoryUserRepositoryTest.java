package com.kakao.cafe;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

	MemoryUserRepository repository = new MemoryUserRepository();

	@AfterEach
	public void afterEach() {
		repository.clearUserInformationList();
	}

	@DisplayName("UserInformation 객체가 주어지면 UserInformation 객체의 사용자 정보 데이터를 저장할 수 있는가?")
	@Test
	void savaUserInformation() {
		// given
		UserInformation userInformation = new UserInformation();
		userInformation.setUserId("ikjo");
		userInformation.setPassword("1234");
		userInformation.setName("조명익");
		userInformation.setEmail("auddlr100@naver.com");

		// when
		repository.savaUserInformation(userInformation);

		// then
		UserInformation result = repository.findUserInformationById(userInformation.getUserId()).get();
		assertThat(result).isEqualTo(userInformation);
	}

	@DisplayName("사용자 ID가 주어지면 UserInformation 객체의 사용자 정보 데이터를 조회할 수 있는가?")
	@Test
	void findUserInformationById() {
		// given
		UserInformation userInformation = new UserInformation();
		userInformation.setUserId("ikjo");
		userInformation.setPassword("1234");
		userInformation.setName("조명익");
		userInformation.setEmail("auddlr100@naver.com");
		repository.savaUserInformation(userInformation);

		// when
		UserInformation result = repository.findUserInformationById(userInformation.getUserId()).get();

		// then
		assertThat(result).isEqualTo(userInformation);
	}
}
