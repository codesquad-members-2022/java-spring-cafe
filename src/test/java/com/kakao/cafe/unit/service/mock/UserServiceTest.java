package com.kakao.cafe.unit.service.mock;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginDto;
import com.kakao.cafe.dto.UserDto;
import com.kakao.cafe.exception.DuplicateException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
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
@DisplayName("UserService mock 단위 테스트")
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

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
    @DisplayName("회원가입하면 저장소에 저장된다")
    public void registerTest() {
        // given
        UserDto userDto = new UserDto("userId", "userPassword", "userName", "user@example.com");

        given(userRepository.save(any()))
            .willReturn(user);

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        User savedUser = userService.register(userDto);

        // then
        then(savedUser.getUserId()).isEqualTo("userId");
        then(savedUser.getPassword()).isEqualTo("userPassword");
        then(savedUser.getName()).isEqualTo("userName");
        then(savedUser.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("유저가 회원가입할 때 이미 있는 유저 아이디면 예외를 반환한다")
    public void registerValidationTest() {
        // given
        UserDto userDto = new UserDto("userId", "otherPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        Throwable throwable = catchThrowable(() -> userService.register(userDto));

        // when, then
        then(throwable)
            .isInstanceOf(DuplicateException.class)
            .hasMessage(ErrorCode.DUPLICATE_USER.getMessage());
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
        then(users).containsExactlyElementsOf(List.of(user));
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저를 조회한다")
    public void findUserTest() {
        // given
        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        User findUser = userService.findUser("userId");

        // then
        then(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조화하면 예외를 반환한다")
    public void findUserExceptionTest() {
        // given
        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.findUser(any()));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("변경할 유저 정보를 입력하면 저장소의 유저 정보를 변경한다")
    public void updateUserTest() {
        // given
        UserDto userDto = new UserDto("userId", "userPassword", "otherName",
            "other@example.com");

        User changedUser = new User.Builder()
            .userNum(1)
            .userId("userId")
            .password("userPassword")
            .name("other")
            .email("other@example.com")
            .build();

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        given(userRepository.save(any()))
            .willReturn(changedUser);

        // when
        User updatedUser = userService.updateUser(userDto);

        then(updatedUser.getUserId()).isEqualTo("userId");
        then(updatedUser.getPassword()).isEqualTo("userPassword");
        then(updatedUser.getName()).isEqualTo("other");
        then(updatedUser.getEmail()).isEqualTo("other@example.com");
    }

    @Test
    @DisplayName("유저 정보 변경 시 변경할 유저가 존재하지 않으면 예외를 반환한다")
    public void updateUserNotFoundTest() {
        // given
        UserDto userDto = new UserDto("otherId", "userPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(userDto));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("유저 정보 변경 시 유저 아이디가 일치하지 않으면 예외를 반환한다")
    public void updateUserIncorrectUserIdTest() {
        // given
        UserDto userDto = new UserDto("otherId", "userPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(userDto));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

    @Test
    @DisplayName("유저 정보 변경 시 비밀번호가 일치하지 않으면 예외를 반환한다")
    public void updateUserIncorrectPasswordTest() {
        // given
        UserDto userDto = new UserDto("userId", "otherPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(userDto));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

    @Test
    @DisplayName("로그인 시 기존 유저 정보와 일치하면 유저 정보를 반환한다")
    public void loginTest() {
        // given
        LoginDto loginDto = new LoginDto("userId", "userPassword");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        User user = userService.login(loginDto);

        // then
        then(user.getUserId()).isEqualTo("userId");
    }

    @Test
    @DisplayName("로그인 시 존재하지 않는 유저 로그인 정보를 입력하면 예외를 반환한다")
    public void loginUserNotFoundTest() {
        // given
        LoginDto loginDto = new LoginDto("newId", "userPassword");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.login(loginDto));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("로그인 시 일치하지 않는 비밀번호를 입력하면 예외를 반환한다")
    public void loginIncorrectUserTest() {
        // given
        LoginDto loginDto = new LoginDto("userId", "otherPassword");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        Throwable throwable = catchThrowable(() -> userService.login(loginDto));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

}
