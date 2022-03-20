package com.kakao.cafe.repository.user;

import com.kakao.cafe.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class UserJdbcRepositoryTest {

    @Autowired
    DataSource dataSource;
    UserRepository repository;

    @BeforeEach
    void before() {
        repository = new UserJdbcRepository(dataSource);
    }

    @Test
    @DisplayName("데이터를 저장한다.")
    void save() {
        User user = new User("forky", "1111", "hello@spring.com", "퐄퐄퐄");

        User result = repository.save(user);

        assertThat(result.getUserId()).isEqualTo("forky");
        assertThat(result.getEmail()).isEqualTo("hello@spring.com");
        assertThat(result.getName()).isEqualTo("퐄퐄퐄");
    }

    @Test
    @DisplayName("유저 id 값으로 데이터 조회한다.")
    void findByUserId() {
        User user = new User("forky", "1111", "hello@spring.com", "퐄퐄퐄");
        repository.save(user);

        User result = repository.findByUserId("forky").orElse(null);

        assertThat(result.getUserId()).isEqualTo("forky");
        assertThat(result.getName()).isEqualTo("퐄퐄퐄");
    }

    @Test
    @DisplayName("유저 전체 목록을 조회한다.")
    void findAll() {
        User user1 = new User("forky", "1111", "hello@spring.com", "퐄퐄퐄");
        User user2 = new User("kukim", "1111", "hello@spring.com", "굿김");
        User user3 = new User("phil", "1111", "hello@spring.com", "오마카세컷");

        repository.save(user1);
        repository.save(user2);
        repository.save(user3);

        List<User> allUsers = repository.findAll();

        assertThat(allUsers.size()).isEqualTo(3);

    }
}
