package com.kakao.cafe.unit;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.User;
import com.kakao.cafe.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepository();

    User user;

    @BeforeEach
    public void init() {
        user = userRepository.save(
            new User("userId", "password", "name", "email@example.com"));
    }

    @Test
    @DisplayName("유저 객체를 저장한다")
    public void saveTest() {
        // then
        assertThat(user.getUserNum()).isEqualTo(userRepository.getUserNum());
        assertThat(user.getUserId()).isEqualTo("userId");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email@example.com");
    }

    @Test
    @DisplayName("모든 유저 객체를 조회한다")
    public void findAllTest() {
        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users).containsExactly(user);
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저 객체를 조회한다")
    public void findByUserIdTest() {
        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        assertThat(findUser).isNotEmpty().isEqualTo(findUser);
    }

}