package com.kakao.cafe.repository;

import com.kakao.cafe.controller.UserForm;
import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearUserList();
    }

    @Test
    public void save() {
        User user = new User(new UserForm("sampleId", "sampleName", "sample@email.com"));
        User result = repository.save(user);

//        Assertions.assertEquals(user.getUserId(), result.getUserId());
//        Assertions.assertEquals(user.getName(), result.getName());
//        Assertions.assertEquals(user.getEmail(), result.getEmail());
//        user와 result는 동일한 객체여야 하므로 객체 자체가 같은지 테스트해야 함.

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findById() {
        User user = new User(new UserForm("sampleId", "sampleName", "sample@email.com"));
        repository.save(user);
        User result = repository.findById("sampleId").get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findAll() {
        User firstUser = new User(new UserForm("sampleId", "sampleNameOne", "sampleOne@email.com"));
        User secondUser = new User(new UserForm("sampleIdTwo", "sampleNameTwo", "sampleTwo@email.com"));

        List<User> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);

        repository.save(firstUser);
        repository.save(secondUser);

        List<User> result = repository.findAll();

        assertThat(result).isEqualTo(users);
    }
}
