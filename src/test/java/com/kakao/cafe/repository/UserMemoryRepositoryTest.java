package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserMemoryRepositoryTest {
    UserMemoryRepository repository = new UserMemoryRepository();

    @AfterEach
    public void afterEach() { repository.clearStore(); }

    @Test
    void save() {
        User user = new User("abc123@gmail.com","ABC","abc");
        repository.save(user);

        User result = repository.findByEmail(user.getEmail()).get();

        assertThat(user).isEqualTo(result);
    }

    @Test
    void findByUserId() {
        User user = new User("abc123@gmail.com","ABC","abc");
        repository.save(user);

        User result = repository.findByUserId("ABC").get();

        assertThat(result).isEqualTo(user);
    }

    @Test
    void findByEmail() {
        User user = new User("abc123@gmail.com","ABC","abc");
        repository.save(user);

        User result = repository.findByEmail("abc123@gmail.com").get();

        assertThat(result).isEqualTo(user);
    }

    @Test
    void findAll() {
        User user1 = new User("abc123@gmail.com","ABC","abc");
        repository.save(user1);

        User user2 = new User("def123@gmail.com","DEF","def");
        repository.save(user2);

        List<User>result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void clearStore() {
        User user1 = new User("abc123@gmail.com","ABC","abc");
        repository.save(user1);

        User user2 = new User("def123@gmail.com","DEF","def");
        repository.save(user2);

        repository.clearStore();
        List<User>result = repository.findAll();

        assertThat(result.size()).isEqualTo(0);
    }
}
