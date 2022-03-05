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
        user1.setNickname("bc");
        user1.setPassword("1234");
        userService.join(user1);

        User user = userService.findById(user1.getId());
        assertThat(user.getNickname()).isEqualTo(user1.getNickname());
        assertThat(user.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    @DisplayName("회원가입시 이미 등록되어있는 닉네임을 입력하면 예외를 발생시킨다")
    void join_validateDuplicateNickname() {
        User user1 = new User();
        user1.setNickname("bc");
        user1.setPassword("1234");
        userService.join(user1);

        User user2 = new User();
        user2.setNickname("bc");
        user2.setPassword("1234555");

        assertThatThrownBy(() -> userService.join(user2))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(ErrorMessage.EXISTING_NICKNAME.get());
    }

    @Test
    @DisplayName("회원가입을 하면 id가 1부터 오름차순으로 배정된다")
    void join_assign_id() {
        User user1 = new User();
        user1.setNickname("bc");
        user1.setPassword("1234");
        userService.join(user1);

        User user2 = new User();
        user2.setNickname("BBBB");
        user2.setPassword("1234555");
        userService.join(user2);

        assertThat(user1.getId()).isEqualTo(1);
        assertThat(user2.getId()).isEqualTo(2);

    }
}
