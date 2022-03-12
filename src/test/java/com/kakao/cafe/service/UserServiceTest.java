package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.UserJoinRequestDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    UserJoinRequestDto userJoinRequestDto;
    UserUpdateRequestDto userUpdateRequestDto;
    User user;

    @BeforeEach
    void setUp() {
        String testUserId = "testId";
        String testPassword = "password";
        String testName = "suntory";
        String testEmail = "test@test.co.kr";

        String newPassword = "newpassword";
        String newName = "newSuntory";
        String newEmail = "new@test.co.kr";

        userJoinRequestDto = new UserJoinRequestDto(testUserId, testPassword, testName, testEmail);
        userUpdateRequestDto = new UserUpdateRequestDto(testUserId, testPassword, newPassword, newName, newEmail);
        user = userJoinRequestDto.toEntity();
    }

    @Test
    @DisplayName("신규 회원가입 시 예외없이 잘 동작한다.")
    void join() {
        // given
        given(userRepository.findByUserId(any()))
                .willReturn(Optional.empty());

        // then
        assertThatCode(() -> userService.join(userJoinRequestDto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원가입 시 중복회원이 존재하면 예외가 발생한다.")
    void joinDuplicate() {
        // given
        String userId = "testId";
        given(userRepository.findByUserId(userId))
                .willReturn(Optional.ofNullable(user));

        // then
        assertThatThrownBy(() -> userService.join(userJoinRequestDto))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("userId로 검색하면 회원이 조회된다")
    void findByUserId() {
        // given
        String userId = "testId";
        given(userRepository.findByUserId(userId))
                .willReturn(Optional.ofNullable(user));

        // when
        User byUserId = userService.findByUserId(userId);

        // then
        assertThat(byUserId.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("userId로 존재하지 않는 회원을 검색하면 예외가 발생한다")
    void findByUserIdNotFound() {
        // given
        String userId = "testId";
        given(userRepository.findByUserId(userId))
                .willReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> userService.findByUserId(userId));
    }

    @Test
    @DisplayName("회원목록이 검색된다")
    void findUsers() {
        // given
        String testUserId2 = "testId2";
        String testPassword2 = "password2";
        String testName2 = "santori";
        String testEmail2 = "test2@test.co.kr";

        User user2 = new User(testUserId2, testPassword2, testName2, testEmail2);
        List<User> userList = List.of(user, user2);

        given(userRepository.findAll()).willReturn(userList);

        // when
        List<User> findUsers = userService.findUsers();

        // then
        assertThat(findUsers).isEqualTo(userList);
        assertThat(findUsers).hasSize(userList.size());
        assertThat(findUsers.get(0).getUserId()).isEqualTo(user.getUserId());
        assertThat(findUsers.get(1).getUserId()).isEqualTo(user2.getUserId());
    }

    @Test
    @DisplayName("회원정보를 수정하면 예외없이 반영된다")
    void update() {
        // given
        String userId = "testId";
        given(userRepository.findByUserId(userId)).willReturn(Optional.ofNullable(user));

        // then
        assertThatCode(() -> userService.update(userUpdateRequestDto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원정보를 수정할 때 userId가 비밀번호가 일치하지 않으면 예외가 발생한다.")
    void updateFail() {
        // given
        String wrongPassword = "wrong";
        String newPassword = "newpassword";
        String newName = "newSuntory";
        String newEmail = "new@test.co.kr";

        String userId = "testId";
        UserUpdateRequestDto dto = new UserUpdateRequestDto(userId, wrongPassword, newPassword, newName, newEmail);
        given(userRepository.findByUserId(userId)).willReturn(Optional.ofNullable(user));

        // then
        assertThrows(IllegalArgumentException.class, () -> userService.update(dto));
    }
}
