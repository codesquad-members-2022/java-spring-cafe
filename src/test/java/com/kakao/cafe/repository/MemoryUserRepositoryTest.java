package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @AfterEach
    void afterEach() {
        memoryUserRepository.clearStore();
    }

    @Test
    void save() {
        // given
        User user = new User("user", "123123", "김김김", "user@example.com");

        // when
        memoryUserRepository.save(user);

        // then
        User savedUser = memoryUserRepository.findById(user.getId());
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void findAll() {
        // given
        User user1 = new User("user1", "123123", "김김김", "user1@example.com");
        User user2 = new User("user2", "456456", "이이이", "user2@example.com");

        memoryUserRepository.save(user1);
        memoryUserRepository.save(user2);

        // when
        List<User> users = memoryUserRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user1, user2);
    }

    @Test
    void update() {
        // given
        User user = new User("user", "123123", "김김김", "user@example.com");
        memoryUserRepository.save(user);
        Long userId = user.getId();

        // when
        User updateParam = new User("updateParam", "456456", "이이이", "updateParam@example.com");
        memoryUserRepository.update(userId, updateParam);

        User findUser = memoryUserRepository.findById(userId);

        // then
        assertThat(findUser.getEmail()).isEqualTo(updateParam.getEmail());
        assertThat(findUser.getUserId()).isEqualTo(updateParam.getUserId());
        assertThat(findUser.getPassword()).isEqualTo(updateParam.getPassword());
    }
}
