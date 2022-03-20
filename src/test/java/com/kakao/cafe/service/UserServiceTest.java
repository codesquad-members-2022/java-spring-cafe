package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.UserMemorySaveRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserMemorySaveRepository userRepository;
    private UserService userService;
    private User userA, userB;

    @BeforeEach
    void testSetUp() {
        userRepository = new UserMemorySaveRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    void testAfterEach() {
        userRepository.clearStore();
    }

    @Test
    @DisplayName("이미 가입된 회원의 이메일을 사용해 다시 가입하는 경우 예외가 발생해야 한다.")
    void signUp() {
        // given
        userA = new User("emailA", "userIdA", "nameA", "pawA");
        userB = new User("emailA", "userIdB", "nameB", "pawB");
        // when
        userService.signUp(userA);
        // then
        assertThatThrownBy(() -> {
            userService.signUp(userB);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 회원입니다.");
    }


    @Test
    @DisplayName("등록되지 않은 유저의 ID를 가지고 유저를 검색하는 경우 예외가 발생해야 한다.")
    void findIdUser() {
        // given
        userA = new User("emailA", "userIdA", "nameA", "pawA");
        // when
        String unregisteredUserId = "userB";
        // then
        assertThatThrownBy(() -> {
            userService.findIdUser(unregisteredUserId);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("등록되지 않은 유저의 Email을 가지고 유저를 검색하는 경우 예외가 발생해야 한다.")
    void findEmailUser() {
        // given
        userA = new User("emailA", "userIdA", "nameA", "pawA");
        // when
        String unregisteredUserEmail = "emailB";
        // then
        assertThatThrownBy(() -> {
            userService.findEmailUser(unregisteredUserEmail);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("개인정보 수정 시 기존 패스워드와 다른 패스워드를 입력하는 경우 예외가 발생해야 한다.")
    void validatePasswordTest() {
        // given
        userA = new User("emailA", "userIdA", "nameA", "pawA");
        userRepository.userSave(userA);
        UserRequestDto requestDto = new UserRequestDto("userIdA", "changeName", "changeEmail", "pawB");
        // when
        assertThatThrownBy(() -> {
            userService.update(requestDto);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }
}
