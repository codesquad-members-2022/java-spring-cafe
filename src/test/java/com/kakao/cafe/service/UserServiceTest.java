package com.kakao.cafe.service;

import com.kakao.cafe.controller.UserForm;
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
        User user = new User("abc123@gmail.com","ABC","abc");

        // when
        String saveEmail = userService.join(user);

        // then
        User findUser = userService.lookForOneUserEmail(saveEmail).get();
        assertThat(user.isSameEmail(findUser.getEmail()));
    }

    @Test
    void findUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User("abc123@gmail.com","ABC","abc");
        User user2 = new User("def123@gmail.com","DEF","def");

        users.add(user1);
        users.add(user2);

        userService.join(user1);
        userService.join(user2);

        List<User> result = userService.findUsers();

        assertThat(result).isEqualTo(users);
    }

    @Test
    void lookForOneUserEmail() {
        User user = new User("abc123@gmail.com","ABC","abc");
        userService.join(user);

        assertThat(userService.lookForOneUserEmail(user.getEmail()).get().isSameEmail("abc123@gmail.com"));
    }

    @Test
    void lookForOneUserId() {
        User user = new User("abc123@gmail.com","ABC","abc");
        userService.join(user);

        assertThat(userService.lookForOneUserId(user.getUserId()).get()).isEqualTo(user);
    }
}
