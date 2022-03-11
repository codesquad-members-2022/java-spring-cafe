package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.memory.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();
    User user1, user2;

    @BeforeEach
    void init() {
        // given
        user1 = new User("testA", "1234", "testA", "testA@naver.com");
        user2 = new User("testB", "5678", "testB", "testB@gamil.com");
    }

    @Test
    void saveTest() {
        // when
        Long saveId = repository.save(user1);

        // then
        User findUser = repository.findById("testA").get();
        assertThat(user1).isEqualTo(findUser);
    }

    @Test
    void InvalidFindTest() {
        assertThat(repository.findById("noUser").isEmpty()).isTrue();
    }

    @Test
    void findAllTest() {
        // when
        repository.save(user1);
        repository.save(user2);

        // then
        List<User> members = repository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void deleteTest() {
        // when
        Long userId = repository.save(user1);
        repository.save(user2);
        boolean isDeleted = repository.delete("testA");

        // then
        assertThat(isDeleted).isTrue();
        assertThat(repository.findById("testA").isEmpty()).isTrue();
    }

    @Test
    void updateTest() {
        // when
        repository.save(user1);
        repository.save(user2);
        User updateUserInfo = new User("testB", "7777", "testB", "testB@gamil.com");
        repository.update("testA", updateUserInfo);

        // then
        User findUser = repository.findById("testA").get();
        assertThat(findUser.getUserId()).isEqualTo("testB");
    }

    @AfterEach
    void afterEach() {
        repository.clearStore();
    }

}
