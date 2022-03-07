package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    UserService userService;
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach() {
        userRepository.clear();
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

    @Test
    @DisplayName("전체 회원 목록 조회")
    void findUsers() {
        //given
        User user1 = new User();
        user1.setUserId("test1");
        user1.setName("테스트");
        user1.setPassword("1234");
        user1.setEmail("test1@gmail.com");

        User user2 = new User();
        user1.setUserId("test2");
        user1.setName("테스트2");
        user1.setPassword("1234");
        user1.setEmail("test2@gmail.com");

        userService.join(user1);
        userService.join(user2);

        //when
        List<User> result = userService.findUsers();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 회원 정보 조회")
    void findOne() {
        //given
        User user1 = new User();
        user1.setUserId("test1");
        user1.setName("테스트");
        user1.setPassword("1234");
        user1.setEmail("test1@gmail.com");

        userService.join(user1);

        //when
        User result = userService.findOne(user1.getUserId()).get();

        //then
        assertThat(result.getUserId()).isEqualTo(user1.getUserId());
    }
}
