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
        User user = new User("user@email.com", "user", "123123");

        // when
        memoryUserRepository.save(user);

        // then
        User savedUser = memoryUserRepository.findById(user.getId());
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void findAll() {
        // given
        User user1 = new User("user1@email.com", "user1", "123123");
        User user2 = new User("user2@email.com", "user2", "456456");

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
        User user = new User("user@email.com", "user", "123123");
        memoryUserRepository.save(user);
        Long userId = user.getId();

        // when
        User updateParam = new User("updateParam@email.com", "updateParam", "456456");
        memoryUserRepository.update(userId, updateParam);

        User findUser = memoryUserRepository.findById(userId);

        // then
        assertThat(findUser.getEmail()).isEqualTo(updateParam.getEmail());
        assertThat(findUser.getUserId()).isEqualTo(updateParam.getUserId());
        assertThat(findUser.getPassword()).isEqualTo(updateParam.getPassword());
    }
}
