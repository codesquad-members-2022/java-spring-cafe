package com.kakao.cafe.repository;

import com.kakao.cafe.config.QueryLoader;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.db.DbUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@JdbcTest
@Import(QueryLoader.class)
@Sql("classpath:/schema-h2.sql")
public class JdbcUserRepositoryTest {

    private final DbUserRepository repository;

    @Autowired
    public JdbcUserRepositoryTest(DataSource dataSource, QueryLoader queryLoader) {
        this.repository = new DbUserRepository(dataSource, queryLoader);
    }

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("Shine", "1234", "Shine", "shine@naver.com");

    }

    @Test
    public void saveUserTest() {
        // when
        Long saveId = repository.save(user);
        Optional<User> findUser = repository.findByUserId(user.getUserId());

        // then
        then(findUser).hasValueSatisfying(user -> {
            then(user.getUserId()).isEqualTo("Shine");
            then(user.getPassword()).isEqualTo("1234");
            then(user.getName()).isEqualTo("Shine");
            then(user.getEmail()).isEqualTo("shine@naver.com");
        });
    }

    @Test
    public void findByUserIdTest() {
        // given
        repository.save(user);

        // when
        User findUser = repository.findByUserId(user.getUserId()).get();

        // then
        then(findUser).isEqualTo(user);
    }

    @Test
    public void findEmptyTest() {
        // when
        Optional<User> findUser = repository.findByUserId(user.getUserId());

        // then
        then(findUser).isEqualTo(Optional.empty());
    }

    @Test
    public void findUsersTest() {
        // given
        User user2 = new User("Shine2", "5678", "Shine2", "shine2@naver.com");
        repository.save(user);
        repository.save(user2);

        // when
        List<User> users = repository.findAll();

        // then
        then(users).containsExactly(user, user2);
        Assertions.assertThat(users.size()).isEqualTo(2);
    }
}
