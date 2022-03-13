package com.kakao.cafe.user.domain;

import static com.kakao.cafe.user.infra.MemoryUserRepositoryTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
	private User user;

	@BeforeEach
	void beforeEach() {
		user = getUser();
	}

	@Test
	@DisplayName("비밀번호 오류제한 횟수 초과시 요청된 입력제한상태 변경 결과로 비밀번호 입력 제한상태를 확인한다.")
	void restrict_attempt_input_password_when_enter_more_than_3_times_incorrectly() {
		user.restrictTheAttemptOfInputPassword();

		boolean actual = user.isAllowedStatusOfPasswordEntry();

		assertThat(actual).isFalse();
	}
}
