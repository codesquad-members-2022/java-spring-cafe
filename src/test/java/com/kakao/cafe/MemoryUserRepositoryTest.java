package com.kakao.cafe;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

	MemoryUserRepository repository = new MemoryUserRepository();

	@AfterEach
	void afterEach() {
		repository.clearUserInformationList();
	}

	@DisplayName("UserInformation 객체가 주어지면 해당 UserInformation 객체의 사용자 정보 데이터를 저장할 수 있는가?")
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

	@DisplayName("특정 사용자 ID에 해당하는 UserInformation 객체의 사용자 정보 데이터를 조회할 수 있는가?")
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

	@DisplayName("저장된 사용자 정보가 2개일 때 사용자 정보 데이터 2개를 모두 조회할 수 있는가?")
	@Test
	void findAllUserInformation() {
		// given
		UserInformation userInformation1 = new UserInformation();
		userInformation1.setUserId("ikjo");
		userInformation1.setPassword("1234");
		userInformation1.setName("조명익");
		userInformation1.setEmail("auddlr100@naver.com");
		repository.savaUserInformation(userInformation1);

		UserInformation userInformation2 = new UserInformation();
		userInformation2.setUserId("ikjo");
		userInformation2.setPassword("1234");
		userInformation2.setName("조명익");
		userInformation2.setEmail("auddlr100@naver.com");
		repository.savaUserInformation(userInformation2);

		// when
		List<UserInformation> userInformation = repository.findAllUserInformation();

		// then
		assertThat(userInformation.size()).isEqualTo(2);
	}
}
