package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    UserRepository userRepository;

    User user;

    @BeforeEach
    void setup() {
        userRepository = new MemoryUserRepository();
        user = new User.UserBuilder("testId","testPw")
            .setName("testName")
            .setEmail("test@test.com")
            .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("회원 아이디를 통해 레포지토리에서 회원 객체를 검색할 수 있다.")
    void 회원_객체_조회_테스트() {
        // given
        String userId = "testId";

        // when
        User resultUser = userRepository.findById(userId).get();

        // then
        assertThat(resultUser.getId()).isEqualTo(user.getId());
        assertThat(resultUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(resultUser.getName()).isEqualTo(user.getName());
        assertThat(resultUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("모든 회원 객체를 레포지토리에서 조회할 수 있다.")
    void 모든_회원_조회_테스트() {
        // given
        User secondUser = new User.UserBuilder("testId2","testPw2")
            .setName("testName2")
            .setEmail("test2@test.com")
            .build();

        // when
        userRepository.save(secondUser);

        // then
        assertThat(userRepository.findAll()).hasSize(2);
        assertThat(userRepository.findAll()).contains(user);
        assertThat(userRepository.findAll()).contains(secondUser);
    }
}
