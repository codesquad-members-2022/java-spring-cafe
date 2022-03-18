package com.kakao.cafe.unit.service.stub;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserLoginRequest;
import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.dto.UserSaveRequest;
import com.kakao.cafe.exception.DuplicateException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.session.SessionUser;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserService stub 단위 테스트")
public class UserServiceTest {

    private UserService userService;
    private UserResponse userResponse;
    private SessionUser sessionUser;

    @BeforeEach
    public void setUp() {
        userService = new UserService(new UserStubRepository());

        userResponse = new UserResponse(1, "userId", "userPassword", "userName",
            "user@example.com");
        sessionUser = new SessionUser(1, "userId", "userPassword", "userName",
            "user@example.com");
    }

    @Test
    @DisplayName("회원가입하면 저장소에 저장된다")
    public void registerTest() {
        // given
        UserSaveRequest request = new UserSaveRequest("newId", "otherPassword", "otherName",
            "other@example.com");

        // when
        SessionUser savedUser = userService.register(request);

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
        UserSaveRequest request = new UserSaveRequest("dupId", "userPassword", "userName",
            "user@example.com");

        // when
        Throwable throwable = catchThrowable(() -> userService.register(request));

        // when, then
        then(throwable)
            .isInstanceOf(DuplicateException.class)
            .hasMessage(ErrorCode.DUPLICATE_USER.getMessage());
    }

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void findUsersTest() {
        // when
        List<UserResponse> users = userService.findUsers();

        // then
        then(users).containsExactlyElementsOf(List.of(userResponse));
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저를 조회한다")
    public void findUserTest() {
        // when
        UserResponse findUser = userService.findUser("userId");

        // then
        then(findUser).isEqualTo(userResponse);
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조화하면 예외를 반환한다")
    public void findUserExceptionTest() {
        // when
        Throwable throwable = catchThrowable(() -> userService.findUser(null));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("변경할 유저 정보를 입력하면 저장소의 유저 정보를 변경한다")
    public void updateUserTest() {
        // given
        UserSaveRequest request = new UserSaveRequest("userId", "userPassword", "otherName",
            "other@example.com");

        // when
        UserResponse updatedUser = userService.updateUser(sessionUser, request);

        then(updatedUser.getUserId()).isEqualTo("userId");
        then(updatedUser.getPassword()).isEqualTo("userPassword");
        then(updatedUser.getName()).isEqualTo("otherName");
        then(updatedUser.getEmail()).isEqualTo("other@example.com");
    }

    @Test
    @DisplayName("유저 정보 변경 시 변경할 유저가 존재하지 않으면 예외를 반환한다")
    public void updateUserNotFoundTest() {
        // given
        SessionUser sessionOther = new SessionUser(1, "newId", "userPassword", "userName",
            "user@example.com");

        UserSaveRequest request = new UserSaveRequest("newId", "userPassword", "otherName",
            "other@example.com");

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(sessionOther, request));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("유저 정보 변경 시 비밀번호가 일치하지 않으면 예외를 반환한다")
    public void updateUserIncorrectPasswordTest() {
        // given
        UserSaveRequest request = new UserSaveRequest("userId", "otherPassword",
            "otherName", "other@example.com");

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(sessionUser, request));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

    @Test
    @DisplayName("로그인 시 기존 유저 정보와 일치하면 유저 정보를 반환한다")
    public void loginTest() {
        // given
        UserLoginRequest request = new UserLoginRequest("userId", "userPassword");

        // when
        SessionUser sessionUser = userService.login(request);

        // then
        then(sessionUser.getUserId()).isEqualTo("userId");
    }

    @Test
    @DisplayName("로그인 시 존재하지 않는 유저 로그인 정보를 입력하면 예외를 반환한다")
    public void loginUserNotFoundTest() {
        // given
        UserLoginRequest request = new UserLoginRequest("newId", "userPassword");

        // when
        Throwable throwable = catchThrowable(() -> userService.login(request));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("로그인 시 일치하지 않는 비밀번호를 입력하면 예외를 반환한다")
    public void loginIncorrectUserTest() {
        // given
        UserLoginRequest request = new UserLoginRequest("userId", "otherPassword");

        // when
        Throwable throwable = catchThrowable(() -> userService.login(request));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

    private static class UserStubRepository implements UserRepository {

        private final User user = User.createWithInput("userId", "userPassword", "userName",
            "user@example.com");

        @Override
        public User save(User user) {
            if (user.getUserId().equals("newId")) {
                return this.user;
            }
            if (user.getUserId().equals("dupId")) {
                throw new DuplicateException(ErrorCode.DUPLICATE_USER);
            }
            return user;
        }

        @Override
        public List<User> findAll() {
            return List.of(user);
        }

        @Override
        public Optional<User> findByUserId(String userId) {
            return userId == null || userId.equals("newId") ? Optional.empty() : Optional.of(user);
        }

        @Override
        public void deleteAll() {

        }
    }

}
