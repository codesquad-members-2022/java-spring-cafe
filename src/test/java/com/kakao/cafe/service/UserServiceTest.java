package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.MemoryUserRepository;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new MemoryUserRepository());
    }

    @AfterEach
    void clear() {
        userService.clearRepository();
    }

    @Test
    @DisplayName("중복된 Id를 가진 user를 회원가입할 시 예외가 발생한다.")
    void join_duplicateId() {
        User user = new User("ron2", "1234", "로니", "ron2@gmail.com");

        assertThatNoException().isThrownBy(() -> userService.join(user));

        User duplicatedUser = new User("ron2", "1111", "니로", "2ron@naver.com");

        assertThatThrownBy(() -> userService.join(duplicatedUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("findAll()메서드를 호출하면 List<UserResponseDto>를 반환한다.")
    void findAllTest() {
        User user1 = new User("ron2", "1234", "로니", "ron2@gmail.com");
        User user2 = new User("ron3", "1111", "니로", "3ron@naver.com");

        userService.join(user1);
        userService.join(user2);

        List<UserResponseDto> users = userService.findAll();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.stream().map(UserResponseDto::getUserId).anyMatch(user1::isSameId)).isTrue();
        assertThat(users.stream().map(UserResponseDto::getUserId).anyMatch(user2::isSameId)).isTrue();
    }

    @Test
    @DisplayName("findUser(userId)메서드를 호출하면 UserResponseDto를 반환한다.")
    void findUserTest() {
        User user1 = new User("ron2", "1234", "로니", "ron2@gmail.com");
        User user2 = new User("ron3", "1111", "니로", "3ron@naver.com");

        userService.join(user1);
        userService.join(user2);

        UserResponseDto dto = userService.findUser("ron2");

        assertThat(dto.getUserId()).isEqualTo(user1.getUserId());
    }

}
