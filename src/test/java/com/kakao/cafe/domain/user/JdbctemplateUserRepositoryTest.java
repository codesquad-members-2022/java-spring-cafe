package com.kakao.cafe.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;


import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Sql("/ddl.sql")
class JdbctemplateUserRepositoryTest {

    private UserRepository userRepository;
    private User user;

    @Autowired
    public JdbctemplateUserRepositoryTest(DataSource dataSource) {
        this.userRepository = new JdbctemplateUserRepository(dataSource);
    }

    @BeforeEach
    void setUp() {
        user = new User("ron2","1234", "로니", "email@email.com");
    }

    @Test
    @DisplayName("유저 아이디로 해당 유저를 조회할 수 있다.")
    void findByIdTest() {

        userRepository.save(user);

        Optional<User> target = userRepository.findById("ron2");

        target.ifPresent(u -> {
            assertThat(u.getUserId()).isEqualTo("ron2");
            assertThat(u.getPassword()).isEqualTo("1234");
            assertThat(u.getName()).isEqualTo("로니");
            assertThat(u.getEmail()).isEqualTo("email@email.com");
        });
    }

    @Test
    @DisplayName("모든 유저를 조회할 수 있다.")
    void findAllTest() {

        userRepository.save(user);

        List<User> all = userRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
        assertThat(all.contains(user)).isTrue();
    }

}

