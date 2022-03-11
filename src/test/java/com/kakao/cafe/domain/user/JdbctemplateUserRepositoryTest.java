package com.kakao.cafe.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
class JdbctemplateUserRepositoryTest {

    private UserRepository userRepository;
    private User user;

    @Autowired
    public JdbctemplateUserRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
        userRepository = new JdbctemplateUserRepository(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        user = new User("ron2","1234", "로니", "email@email.com");
    }

    @Test
    @DisplayName("유저 아이디로 해당 유저를 조회할 수 있다.")
    void findByIdTest() {

        userRepository.save(user);

        User target = userRepository.findById("ron2").orElseThrow();

        assertThat(target.getUserId()).isEqualTo("ron2");
        assertThat(target.getPassword()).isEqualTo("1234");
        assertThat(target.getName()).isEqualTo("로니");
        assertThat(target.getEmail()).isEqualTo("email@email.com");

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

