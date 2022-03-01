package com.kakao.cafe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
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
    @DisplayName("유저 객체를 저장했을 때 이미 있는 유저 아이디면 예외를 반환한다")
    public void validateTest() {
        // given
        User other = new User("userId", "secret", "other", "other@example.com");

        // when
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> userRepository.save(other));

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 등록된 유저 아이디입니다.");
        assertThat(userRepository.getUserNum()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저 객체를 조회한다")
    public void findBytUserIdTest() {
        // when
        User findUser = userRepository.findByUserId(user.getUserId());

        // then
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저 객체를 조화하면 예외를 반환한다")
    public void findByUserIdExceptionTest() {
        // when
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> userRepository.findByUserId("miller"));

        // then
        assertThat(exception.getMessage()).isEqualTo("등록되지 않은 유저 아이디입니다.");
    }

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void findAllTest() {
        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users).containsExactly(user);
    }

}