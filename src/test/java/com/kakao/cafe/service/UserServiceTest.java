package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.UserSaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("이미 존재하는 userId를 저장할 때 예외가 발생한다.")
    void validateUserId() {
        UserSaveDto userSaveDto = new UserSaveDto("validUserId", "password", "name", "userSaveDto1@gamil.com");
        UserSaveDto validUserSaveDto = new UserSaveDto("validUserId", "password", "name", "userSaveDto2@gamil.com");

        userService.save(userSaveDto);

        assertThatThrownBy(() -> userService.save(validUserSaveDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("이미 존재하는 이메일을 저장할 때 예외가 발생한다.")
    void validateEmail() {
        UserSaveDto userSaveDto = new UserSaveDto("validEmail1", "password", "name", "email@gamil.com");
        UserSaveDto validUserSaveDto = new UserSaveDto("validEmail2", "password", "name", "email@gamil.com");

        userService.save(userSaveDto);

        assertThatThrownBy(() -> userService.save(validUserSaveDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }


}
