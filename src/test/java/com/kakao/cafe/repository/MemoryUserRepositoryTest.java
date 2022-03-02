package com.kakao.cafe.repository;

import com.kakao.cafe.domain.UserInformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

	private UserRepository userRepository = new MemoryUserRepository();

	private UserInformation userInformation;

	@BeforeEach
	void setup() {
		userInformation = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
	}

	@AfterEach
	void close() {
		userRepository.clearUserInformationList();
	}

	@DisplayName("주어진 UserInformation 객체의 사용자 정보 데이터를 저장한다.")
	@Test
	void 사용자_정보_저장() {
		// when
		userRepository.savaUserInformation(userInformation);

		// then
		UserInformation result = userRepository.findUserInformationById(userInformation.getUserId()).get();
		assertThat(result).isEqualTo(userInformation);
	}

	@DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
	@Test
	void 특정_사용자_정보_조회() {
		// given
		userRepository.savaUserInformation(userInformation);

		// when
		UserInformation result = userRepository.findUserInformationById(userInformation.getUserId()).get();

		// then
		assertThat(result).isEqualTo(userInformation);
	}

	@DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
	@Test
	void 모든_사용자_정보_조회() {
		// given
		userRepository.savaUserInformation(userInformation);
		UserInformation userInformation1 = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
		userRepository.savaUserInformation(userInformation1);

		// when
		List<UserInformation> userInformation = userRepository.findAllUserInformation();

		// then
		assertThat(userInformation.size()).isEqualTo(2);
	}
}
