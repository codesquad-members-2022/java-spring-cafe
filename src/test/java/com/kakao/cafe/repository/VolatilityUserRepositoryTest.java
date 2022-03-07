package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class VolatilityUserRepositoryTest {

    @Autowired
    VolatilityUserRepository userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.clear();
    }

    @Test
    void selectAll() {
        //given
        User user1 = User.builder("user1").build();
        userRepository.save(user1);

        User user2 = User.builder("user2").build();
        userRepository.save(user2);

        //when
        List<User> users = userRepository.findAll();

        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void insertUser() {
        //given
        User user = User.builder("user").build();

        //when
        userRepository.save(user);

        //then
        User result = userRepository.findOne(user.getUserId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void selectUser() {
        //given
        User user = User.builder("user").build();
        userRepository.save(user);

        //when
        User result = userRepository.findOne(user.getUserId()).get();

        //then
        assertThat(result).isEqualTo(user);
    }
}
