package com.kakao.cafe.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMemoryRepositoryTest {

    UserRepository userRepository;
    User user;

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
    @DisplayName("userId로 조회하면 회원이 검색된다")
    void saveAndFindByUserId() {
        // given
        String userId = "testId";

        // when
        User resultUser = userRepository.findByUserId(userId).get();

        // then
        assertThat(resultUser).isEqualTo(user);
        assertThat(resultUser.getName()).isEqualTo(user.getName());
        assertThat(resultUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(resultUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("회원 리스트를 조회하면 회원들이 검색된다")
    void findUserList() {
        // given
        User secondUser = new User("testId2", "password", "santori", "test2@test.co.kr");

        // when
        userRepository.save(secondUser);
        List<User> userList = userRepository.findAll();

        // then
        assertThat(userList.size()).isEqualTo(2);
        assertThat(userList.get(0)).isEqualTo(user);
        assertThat(userList.get(1)).isEqualTo(secondUser);
    }

    @Test
    @DisplayName("가입하려는 회원이 이미 가입된 회원이면 정보를 수정한다")
    void isAlreadyJoinedThenUpdate() {
        // given
        String userId = "testId";
        String newPassword = "new";
        String newName = "new_name";
        String newEmail = "new_email@test.co.kr";
        User originalUser = userRepository.findByUserId(userId).get();
        User updateUser = new User(userId, newPassword, newName, newEmail);
        originalUser.update(updateUser);
        userRepository.save(originalUser);

        // when
        User resultUser = userRepository.findByUserId(userId).get();

        // then
        assertThat(resultUser.getUserId()).isEqualTo(userId);
        assertThat(resultUser.getName()).isEqualTo(newName);
        assertThat(resultUser.getPassword()).isEqualTo(newPassword);
        assertThat(resultUser.getEmail()).isEqualTo(newEmail);
    }
}

