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

    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NAME = "user";
    private static final String USER_EMAIL = "user@example.com";

    private static final String OTHER_ID = "otherId";
    private static final String OTHER_PASSWORD = "secret";
    private static final String OTHER_NAME = "other";
    private static final String OTHER_EMAIL = "other@example.com";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL);
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
        assertThat(savedUser.getUserId()).isEqualTo(USER_ID);
        assertThat(savedUser.getPassword()).isEqualTo(USER_PASSWORD);
        assertThat(savedUser.getName()).isEqualTo(USER_NAME);
        assertThat(savedUser.getEmail()).isEqualTo(USER_EMAIL);
    }

    @Test
    @DisplayName("유저가 회원가입할 때 이미 있는 유저 아이디면 예외를 반환한다")
    public void registerValidationTest() {
        // given
        User other = new User(USER_ID, OTHER_PASSWORD, OTHER_NAME, OTHER_EMAIL);

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
        User findUser = userService.findUser(USER_ID);

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
        other.setName(OTHER_NAME);
        other.setEmail(OTHER_EMAIL);

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        given(userRepository.save(any()))
            .willReturn(other);

        // when
        User updatedUser = userService.updateUser(other);

        assertThat(updatedUser.getUserId()).isEqualTo(USER_ID);
        assertThat(updatedUser.getPassword()).isEqualTo(USER_PASSWORD);
        assertThat(updatedUser.getName()).isEqualTo(OTHER_NAME);
        assertThat(updatedUser.getEmail()).isEqualTo(OTHER_EMAIL);
    }

    @Test
    @DisplayName("유저 정보 변경 시 변경할 유저가 존재하지 않으면 예외를 반환한다")
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

    @Test
    @DisplayName("유저 정보 변경 시 유저 아이디가 일치하지 않으면 예외를 반환한다")
    public void updateUserIncorrectUserIdTest() {
        // given
        User other = new User(user);
        other.setUserId(OTHER_ID);

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.updateUser(other));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.INCORRECT_USER.getMessage());
    }

    @Test
    @DisplayName("유저 정보 변경 시 비밀번호가 일치하지 않으면 예외를 반환한다")
    public void updateUserIncorrectPasswordTest() {
        // given
        User other = new User(user);
        other.setPassword(OTHER_PASSWORD);

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.updateUser(other));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.INCORRECT_USER.getMessage());
    }

}
