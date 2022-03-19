package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcTemplateUserRepositoryTest {

    UserRepository userRepository;
    @Autowired
    DataSource dataSource;

    @BeforeEach
    void beforeEach() {
        userRepository = new JdbcTemplateUserRepository(dataSource);
    }

    @Test
    @DisplayName("유저가 잘 저장되는가")
    void save() {
        User user = new User("nathan", "나단", "123123a", "nathan@kakao.com");
        userRepository.save(user);
        User savedUser = userRepository.findByUserId("nathan").orElseThrow();
        assertThat("나단").isEqualTo(savedUser.getName());
    }

    @Test
    @DisplayName("id를 기반으로 조회가 잘 되는가")
    void findById() {
        User savedUser = userRepository.findById(1).orElseThrow();
        assertThat("user1").isEqualTo(savedUser.getUserId());
    }

    @Test
    @DisplayName("userId를 기반으로 조회가 잘 되는가")
    void findByUserId() {
        User savedUser = userRepository.findByUserId("user1").orElseThrow();
        assertThat("유저1").isEqualTo(savedUser.getName());
    }

    @Test
    @DisplayName("모든 유저가 조회되는가")
    void findAll() {
        List<User> users = userRepository.findAll();
        assertThat("유저1").isEqualTo(users.get(0).getName());
        assertThat("유저2").isEqualTo(users.get(1).getName());
        assertThat("유저3").isEqualTo(users.get(2).getName());
    }

    @Test
    @DisplayName("유저 정보 업데이트가 잘 이루어 지는가")
    void update() {
        User user = new User("user4", "변경전유저", "1234a", "user4@aaa.com");
        User updatedUser = new User("user4", "변경후유저", "1234b", "user4@bbb.com");
        userRepository.save(user);
        userRepository.update(updatedUser, 4);
        User savedUser = userRepository.findByUserId("user4").orElseThrow();
        assertThat("변경후유저").isEqualTo(savedUser.getName());
    }
}
