package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserMemoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    UserService userService;
    UserMemoryRepository userRepository;

    @BeforeEach
    public void beforeEach(){
        userRepository = new UserMemoryRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach() { userRepository.clearStore(); }

    @Test
    void join() {
        // given
        User user = new User();
        user.setEmail("abc123@gmail.com");

        // when
        String saveEmail = userService.join(user);

        // then
        User findUser = userService.findOne(saveEmail).get();
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
    }

    @Test
    void findUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        User user2 = new User();
        user1.setEmail("abc123@gmail.com");
        user2.setEmail("def456@gmail.com");

        users.add(user1);
        users.add(user2);

        userService.join(user1);
        userService.join(user2);

        List<User> result = userService.findUsers();

        assertThat(result).isEqualTo(users);
    }

    @Test
    void findOne() {
        User user = new User();
        user.setEmail("abc123@gmail.com");
        userService.join(user);

        assertThat(userService.findOne(user.getEmail()).get()).isEqualTo(user);
    }
}