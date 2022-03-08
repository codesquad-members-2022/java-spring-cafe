package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    @DisplayName("회원가입을 하면 회원 정보가 MemoryUserRepository에 저장된다")
    void join_success() {
        User user1 = new User();
        user1.setNickname("bcdd");
        user1.setPassword("1234");
        userService.join(user1);

        User user = userService.findById(user1.getId());
        assertThat(user.getNickname()).isEqualTo(user1.getNickname());
        assertThat(user.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    @DisplayName("회원가입시 이미 등록되어있는 닉네임 또는 이메일을 입력하면 예외를 발생시킨다")
    void join_validateUniqueNickname() {
        User user1 = new User();
        user1.setEmail("BC@gmail.com");
        user1.setNickname("BCdd");
        user1.setPassword("1234");
        userService.join(user1);

        User user2 = new User();
        user2.setEmail("HARRY@gmail.com");
        user2.setNickname("BCdd");
        user2.setPassword("1234555");

        User user3 = new User();
        user3.setEmail("BC@gmail.com");
        user3.setNickname("Zen");
        user3.setPassword("123335");

        assertThatThrownBy(() -> userService.join(user2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ErrorMessage.EXISTING_NICKNAME.get());

        assertThatThrownBy(() -> userService.join(user3))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ErrorMessage.EXISTING_EMAIL.get());
    }

    @Test
    @DisplayName("회원가입을 하면 id가 1부터 오름차순으로 배정된다")
    void join_assign_id() {
        User user1 = new User();
        user1.setEmail("HARRY@gmail.com");
        user1.setNickname("bc");
        user1.setPassword("1234");
        userService.join(user1);

        User user2 = new User();
        user2.setEmail("BC@gmail.com");
        user2.setNickname("BBBB");
        user2.setPassword("1234555");
        userService.join(user2);

        assertThat(user1.getId()).isEqualTo(1);
        assertThat(user2.getId()).isEqualTo(2);

    }
}
