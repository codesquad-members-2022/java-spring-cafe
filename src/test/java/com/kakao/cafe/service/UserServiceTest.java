package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.User;
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

}
