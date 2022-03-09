package com.kakao.cafe.service;

import com.kakao.cafe.controller.UserForm;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService = new UserService(new MemoryUserRepository());

    @Test
    void join() {
        // given
        User user = new User(new UserForm("sampleId", "sampleName", "sample@email.com"));

        // when
        userService.join(user);

        //then
        assertThat(userService.findById(user.getUserId())).isPresent();


    }

    @Test
    void findById() {
        User user = new User(new UserForm("sampleId", "sampleName", "sample@email.com"));
        userService.join(user);

        assertThat(userService.findById(user.getUserId()).get()).isEqualTo(user);
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();

        User userOne = new User(new UserForm("sampleIdOne", "sampleNameOne", "sampleOne@email.com"));
        User userTwo = new User(new UserForm("sampleIdTwo", "sampleNameTwo", "sampleTwo@email.com"));

        users.add(userOne);
        users.add(userTwo);

        userService.join(userOne);
        userService.join(userTwo);

        List<User> result = userService.getAllUsers();

        assertThat(result).isEqualTo(users);
    }
}