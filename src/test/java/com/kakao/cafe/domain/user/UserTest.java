package com.kakao.cafe.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("수정 회원 정보가 잘못되면 예외가 발생한다")
    void isMismatchThenThrows() {
        // given
        String newId = "wrongUserId";
        String newPassword = "new";
        String newName = "new_name";
        String newEmail = "new_email@test.co.kr";
        User updateUser = new User(newId, newPassword, newName, newEmail);

        // when && then
        Assertions.assertThrows(IllegalArgumentException.class, () -> user.update(updateUser));
    }
}
