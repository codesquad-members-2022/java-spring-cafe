package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    UserService userService;
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        //given
        User user = new User();
        user.setUserId("test1");
        user.setName("테스트");
        user.setPassword("1234");
        user.setEmail("test@gmail.com");

        //when
        String saveUserId = userService.join(user);

        //then
        assertThat(saveUserId).isEqualTo(user.getUserId());
    }
}