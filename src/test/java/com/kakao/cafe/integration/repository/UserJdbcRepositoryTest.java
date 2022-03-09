package com.kakao.cafe.integration.repository;


import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.jdbc.UserJdbcRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:/schema.sql")
@Import({UserJdbcRepository.class, QueryProps.class})
public class UserJdbcRepositoryTest {

    @Autowired
    private UserJdbcRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();
    }

    @Test
    @DisplayName("유저 객체를 저장소에 저장한다")
    public void savePersistTest() {
        // when
        User savedUser = userRepository.save(user);

        // then
        then(savedUser.getUserId()).isEqualTo("userId");
        then(savedUser.getPassword()).isEqualTo("userPassword");
        then(savedUser.getName()).isEqualTo("userName");
        then(savedUser.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("변경된 유저 객체를 저장한다")
    public void saveMergeTest() {
        // given
        userRepository.save(user);

        User changedUser = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("otherName")
            .email("other@example.com")
            .build();

        // when
        User updatedUser = userRepository.save(changedUser);

        // then
        then(updatedUser.getUserId()).isEqualTo("userId");
        then(updatedUser.getPassword()).isEqualTo("userPassword");
        then(updatedUser.getName()).isEqualTo("otherName");
        then(updatedUser.getEmail()).isEqualTo("other@example.com");
    }

    @Test
    @DisplayName("모든 유저 객체를 조회한다")
    public void findAllTest() {
        // given
        userRepository.save(user);

        // when
        List<User> users = userRepository.findAll();

        // then
        then(users).containsExactly(user);
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저 객체를 조회한다")
    public void findByUserIdTest() {
        // given
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        then(findUser).hasValue(user);
    }

    @Test
    @DisplayName("존재하지 않는 유저아이디로 질문 객체를 조회한다")
    public void findNullTest() {
        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        then(findUser).isEqualTo(Optional.empty());
    }
}
