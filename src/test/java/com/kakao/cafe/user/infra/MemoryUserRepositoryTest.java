package com.kakao.cafe.user.infra;

import static com.kakao.cafe.user.domain.UserServiceTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserRepository;

class MemoryUserRepositoryTest {
	private UserRepository userRepository;

	@BeforeEach
	void beforeEach() {
		this.userRepository = new MemoryUserRepository();
	}

	@AfterEach
	void afterEach() {
		this.userRepository.deleteAll();
	}

	@Test
	@DisplayName("사용자 정보를 DB에 저장하고, 저장 된 것을 확인한다.")
	void inserted_user_test() {
		User expected = getUser();

		User actual = userRepository.save(expected);

		assertThat(actual).isSameAs(actual);
	}

	@Test
	@DisplayName("수정된 사용자 정보를 DB에 업데이트 하고, 업데이트 된 결과를 확인한다.")
	void updated_user_test() {
		String changedName = "변경된 사용자 이름";
		User user = getUser();
		User expected = userRepository.save(user);

		expected.changeUserName(changedName);
		User actual = userRepository.save(expected);

		assertThat(actual.getName()).isNotEqualTo(CAFE_USER_NAME);  // 메모리 DB로직상, 매개변수로 전달된 객체가 반환되어 저장된 후 반환된 객체는 변경값을 담게 됩니다.
		assertThat(actual.getId()).isEqualTo(expected.getId());
	}

	private User getUser() {
		return new User(CAFE_USER_ID, CAFE_USER_NAME, CAFE_USER_EMAIL, CAFE_USER_PASSWORD);
	}
}
