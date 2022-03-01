package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();
    User user1, user2;

    @BeforeEach
    void init(){
        // given
        user1 = new User("testA", "1234", "testA", "testA@naver.com");
        user2 = new User("testB", "5678", "testB", "testB@gamil.com");
    }

    @Test
    void saveTest(){
        // when
        Long saveId = repository.save(user1);

        // then
        User findUser = repository.findById(saveId);
        assertThat(user1).isEqualTo(findUser);
    }

    @Test
    void InvalidFindTest(){
        assertThatThrownBy(() -> repository.findById(0L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> repository.findById(3L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findAllTest(){
        // when
        repository.save(user1);
        repository.save(user2);

        // then
        List<User> members = repository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void deleteTest(){
        // when
        Long userId = repository.save(user1);
        repository.save(user2);
        boolean isDeleted = repository.delete(userId);

        // then
        assertThat(isDeleted).isTrue();
        assertThatThrownBy(() -> repository.findById(userId)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateTest(){
        // when
        repository.save(user1);
        repository.save(user2);
        User updateUserInfo = new User("testB", "7777", "testB", "testB@gamil.com");
        repository.update(1L, updateUserInfo);

        // then
        User findUser = repository.findById(1L);
        assertThat(findUser.getUserId()).isEqualTo("testB");
    }

    @AfterEach
    void afterEach(){
        repository.clearStore();
    }

}
