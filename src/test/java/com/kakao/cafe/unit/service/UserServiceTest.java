package com.kakao.cafe.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("userId", "password", "name", "email@example.com");
    }

    @Test
    @DisplayName("회원가입하면 저장소에 저장된다")
    public void registerTest() {
        // given
        given(userRepository.save(user))
            .willReturn(user);

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        User savedUser = userService.register(user);

        // then
        assertThat(savedUser.getUserId()).isEqualTo("userId");
        assertThat(savedUser.getPassword()).isEqualTo("password");
        assertThat(savedUser.getName()).isEqualTo("name");
        assertThat(savedUser.getEmail()).isEqualTo("email@example.com");
    }

    @Test
    @DisplayName("유저가 회원가입할 때 이미 있는 유저 아이디면 예외를 반환한다")
    public void registerValidationTest() {
        // given
        User other = new User("userId", "secret", "other", "other@example.com");

        given(userRepository.findByUserId(other.getUserId()))
            .willReturn(Optional.of(user));

        // when
        CustomException exception = assertThrows(
            CustomException.class, () -> userService.register(other));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.DUPLICATE_USER.getMessage());
    }

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void findUsersTest() {
        // given
        given(userRepository.findAll())
            .willReturn(List.of(user));

        // when
        List<User> users = userService.findUsers();

        // then
        assertThat(users).containsExactlyElementsOf(List.of(user));
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저를 조회한다")
    public void findUserTest() {
        // given
        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        User findUser = userService.findUser(user.getUserId());

        // then
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조화하면 예외를 반환한다")
    public void findUserExceptionTest() {
        // given
        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(
            CustomException.class, () -> userService.findUser(any()));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("변경할 유저 정보를 입력하면 저장소의 유저 정보를 변경한다")
    public void updateUserTest() {
        // given
        User other = new User(user);
        other.setName("other");
        other.setEmail("other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        given(userRepository.save(any()))
            .willReturn(other);

        // when
        User updatedUser = userService.updateUser(other);

        assertThat(updatedUser.getUserNum()).isEqualTo(other.getUserNum());
        assertThat(updatedUser.getUserId()).isEqualTo(other.getUserId());
        assertThat(updatedUser.getPassword()).isEqualTo(other.getPassword());
        assertThat(updatedUser.getName()).isEqualTo(other.getName());
        assertThat(updatedUser.getEmail()).isEqualTo(other.getEmail());
    }

    @Test
    @DisplayName("변경할 유저가 존재하지 않으면 예외를 반환한다")
    public void updateUserNotFoundTest() {
        // given
        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.updateUser(user));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }

}
