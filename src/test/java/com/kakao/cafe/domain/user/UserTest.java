package com.kakao.cafe.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    User user;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        String testUserId = "testId";
        String testPassword = "password";
        String testName = "suntory";
        String testEmail = "test@test.co.kr";
        userRepository = new UserMemoryRepository();
        user = new User(testUserId, testPassword, testName, testEmail);
        userRepository.save(this.user);
    }

    @Test
    @DisplayName("유저 아이디나 비밀번호가 다르면 예외가 발생한다")
    void validateUserIdTest() {
        //given
        String wrongUserId = "wrong";
        String wrongPassword = "wrong";
        //then
        assertThatThrownBy(() -> user.validateUserId(wrongUserId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정하려는 유저 정보가 일치하지 않습니다.");
        assertThatThrownBy(() -> user.validatePassword(wrongPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 일치하지 않습니다");
    }
}
