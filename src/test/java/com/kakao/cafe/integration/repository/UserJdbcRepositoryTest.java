package com.kakao.cafe.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.jdbc.UserJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:/sql/table.sql")
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
        assertThat(savedUser.getUserId()).isEqualTo("userId");
        assertThat(savedUser.getPassword()).isEqualTo("userPassword");
        assertThat(savedUser.getName()).isEqualTo("userName");
        assertThat(savedUser.getEmail()).isEqualTo("user@example.com");
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
        assertThat(updatedUser.getUserId()).isEqualTo("userId");
        assertThat(updatedUser.getPassword()).isEqualTo("userPassword");
        assertThat(updatedUser.getName()).isEqualTo("otherName");
        assertThat(updatedUser.getEmail()).isEqualTo("other@example.com");
    }
}
