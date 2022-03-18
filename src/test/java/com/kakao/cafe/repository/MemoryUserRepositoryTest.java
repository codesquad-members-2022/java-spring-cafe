package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearUserList();
    }

    @Test
    public void save() {
        User user = new User("sampleId", "sampleName", "sample@email.com");
        User result = repository.save(user);

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findById() {
        User user = new User("sampleId", "sampleName", "sample@email.com");
        repository.save(user);
        User result = repository.findById("sampleId").get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findAll() {
        User firstUser = new User("sampleId", "sampleNameOne", "sampleOne@email.com");
        User secondUser = new User("sampleIdTwo", "sampleNameTwo", "sampleTwo@email.com");

        List<User> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);

        repository.save(firstUser);
        repository.save(secondUser);

        List<User> result = repository.findAll();
        assertThat(result).isEqualTo(users);
    }
}
