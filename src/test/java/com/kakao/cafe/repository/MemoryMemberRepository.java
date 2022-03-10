package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepository {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void join() {
        User user = new User();
        user.setName("minzino");

        repository.join(user);
        User result = repository.findById(user.getId()).get();
        assertThat(user).isEqualTo(result);
    }

    @Test
    public void findByName(){
        User firstUser = new User();
        firstUser.setName("meenzino");
        repository.join(firstUser);

        User secondUser = new User();
        secondUser.setName("beenzino");
        repository.join(secondUser);

        User result = repository.findByName("meenzino").get();

        assertThat(result).isEqualTo(firstUser);

    }

    @Test
    public void findAll(){
        User firstUser = new User();
        firstUser.setName("meenzino");
        repository.join(firstUser);

        User secondUser = new User();
        secondUser.setName("beenzino");
        repository.join(secondUser);

        List<User> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
