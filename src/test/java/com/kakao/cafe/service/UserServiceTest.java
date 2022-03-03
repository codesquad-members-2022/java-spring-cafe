package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;
import com.kakao.cafe.repository.MemoryUserRepository;

class UserServiceTest {
    MemoryUserRepository userRepository;
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach() {
        userRepository.clearStore();
    }

    @Test
    @DisplayName("user를 join 시키면 user 정보들이 MemoryUserRepository에 저장된다")
    void join_success() {
        User user1 = new User();
        user1.setEmail("bc@naver.com");
        user1.setNickname("bc");
        user1.setPassword("1234");
        userService.join(user1);

        User testUser = userService.findOne(user1.getId()).get();
        assertThat(testUser.getEmail()).isEqualTo(user1.getEmail());
        assertThat(testUser.getNickname()).isEqualTo(user1.getNickname());
        assertThat(testUser.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    @DisplayName("회원가입 시 중복되는 이메일을 입력하면 예외를 발생시킨다")
    void join_validateDuplicateEmail() {
        User user1 = new User();
        user1.setEmail("bc@naver.com");
        user1.setNickname("bc");
        user1.setPassword("1234");
        userService.join(user1);

        User user2 = new User();
        user2.setEmail("bc@naver.com");
        user2.setNickname("BBBBBB");
        user2.setPassword("1234");

        assertThatThrownBy(() -> userService.join(user2))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(ErrorMessage.EXISTING_EMAIL.get());
    }

    @Test
    @DisplayName("회원가입 시 중복되는 닉메임을 입력하면 예외를 발생시킨다")
    void join_validateDuplicateNickname() {
        User user1 = new User();
        user1.setEmail("bc@naver.com");
        user1.setNickname("bc");
        user1.setPassword("1234");
        userService.join(user1);

        User user2 = new User();
        user2.setEmail("BBBBB@naver.com");
        user2.setNickname("bc");
        user2.setPassword("1234555");

        assertThatThrownBy(() -> userService.join(user2))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(ErrorMessage.EXISTING_NICKNAME.get());
    }
}
