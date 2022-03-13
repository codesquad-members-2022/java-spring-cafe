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
    @DisplayName("유저를 저장할 수 있다.")
    void saveTest() {

        User actual = userRepository.save(user);

        assertThat(actual).isEqualTo(user);
        assertThat(actual.getPassword()).isEqualTo("1234");
        assertThat(actual.getName()).isEqualTo("로니");
        assertThat(actual.getEmail()).isEqualTo("email@email.com");

    }

    @Test
    @DisplayName("유저 아이디로 해당 유저를 조회할 수 있다.")
    void findByIdTest() {

        User actual = userRepository.save(user);

        User target = userRepository.findById(actual.getUserId()).orElseThrow();

        assertThat(target).isEqualTo(actual).isEqualTo(user);
        assertThat(target.getPassword()).isEqualTo("1234");
        assertThat(target.getName()).isEqualTo("로니");
        assertThat(target.getEmail()).isEqualTo("email@email.com");

    }

    @Test
    @DisplayName("유저 정보를 업데이트 할 수 있다.")
    void updateUserTest() {

        User actual = userRepository.save(user);
        User updateUser = new User("ron2", "1234", "differentName", "different@email.com");

        User target = userRepository.save(updateUser);

        assertThat(target).isEqualTo(actual).isEqualTo(updateUser);

        assertThat(target.getPassword()).isEqualTo("1234");
        assertThat(target.getName()).isEqualTo("differentName");
        assertThat(target.getEmail()).isEqualTo("different@email.com");
        assertThat(userRepository.findAll().size()).isEqualTo(1);

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

