package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repositoryimpl.VolatilityUserRepositoryImpl;
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
    VolatilityUserRepositoryImpl userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.clear();
    }

    @Test
    void selectAll() {
        //given
        User user1 = new User(-1, "user1", "1234", "name1", "user1@gmail.com");
        userRepository.save(user1);

        User user2 = new User(-1, "user2", "1234", "name2", "user2@gmail.com");
        userRepository.save(user2);

        //when
        List<User> users = userRepository.findAll();

        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void insertUser() {
        //given
        User user = new User(-1, "user", "1234", "name", "user@gmail.com");

        //when
        userRepository.save(user);

        //then
        User result = userRepository.findOne(user.getUserId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void selectUser() {
        //given
        User user = new User(1, "user", "1234", "name", "user@gmail.com");
        userRepository.save(user);

        //when
        User result = userRepository.findOne(user.getUserId()).get();

        //then
        assertThat(result).isEqualTo(user);
    }
}
