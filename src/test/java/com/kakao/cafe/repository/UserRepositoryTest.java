package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class UserRepositoryTest {

    @Autowired
    Repository<User, String> repository;

    @AfterEach
    public void afterEach() {
        repository.clear();
    }

    @Test
    @DisplayName("전체 사용자 목록을 반환한다.")
    void selectAll() {
        //given
        User user1 = new User(-1, "user1", "1234", "name1", "user1@gmail.com");
        repository.save(user1);

        User user2 = new User(-1, "user2", "1234", "name2", "user2@gmail.com");
        repository.save(user2);

        //when
        List<User> users = repository.findAll();

        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("인자로 주어진 사용자를 저장소에 저장한다.")
    void insertUser() {
        //given
        User user = new User(-1, "user", "1234", "name", "user@gmail.com");

        //when
        repository.save(user);

        //then
        User result = repository.findOne(user.getUserId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("인자로 주어진 ID를 가진 사용자를 저장소에서 찾아 반환한다.")
    void selectUser() {
        //given
        User user = new User(1, "user", "1234", "name", "user@gmail.com");
        repository.save(user);

        //when
        User result = repository.findOne(user.getUserId()).get();

        //then
        assertThat(result).isEqualTo(user);
    }
}
