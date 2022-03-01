package com.kakao.cafe;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

	MemoryUserRepository repository = new MemoryUserRepository();
	UserInformation userInformation;

	@BeforeEach
	void beforeEach() {
		userInformation = new UserInformation();
		userInformation.setUserId("ikjo");
		userInformation.setPassword("1234");
		userInformation.setName("조명익");
		userInformation.setEmail("auddlr100@naver.com");
	}

	@AfterEach
	void afterEach() {
		repository.clearUserInformationList();
	}

	@DisplayName("주어진 UserInformation 객체의 사용자 정보 데이터를 저장한다.")
	@Test
	void 사용자_정보_저장() {
		// given, when
		repository.savaUserInformation(userInformation);

		// then
		UserInformation result = repository.findUserInformationById(userInformation.getUserId()).get();
		assertThat(result).isEqualTo(userInformation);
	}

	@DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
	@Test
	void 특정_사용자_정보_조회() {
		// given
		repository.savaUserInformation(userInformation);

		// when
		UserInformation result = repository.findUserInformationById(userInformation.getUserId()).get();

		// then
		assertThat(result).isEqualTo(userInformation);
	}

	@DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
	@Test
	void 모든_사용자_정보_조회() {
		// given
		UserInformation userInformation1 = new UserInformation();
		userInformation1.setUserId("ikjo");
		userInformation1.setPassword("1234");
		userInformation1.setName("조명익");
		userInformation1.setEmail("auddlr100@naver.com");
		repository.savaUserInformation(userInformation);
		repository.savaUserInformation(userInformation1);

		// when
		List<UserInformation> userInformation = repository.findAllUserInformation();

		// then
		assertThat(userInformation.size()).isEqualTo(2);
	}
}
