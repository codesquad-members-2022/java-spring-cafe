package com.kakao.cafe.unit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    @DisplayName("유저 아이디와 비밀번호가 일치하면 엔티티를 변경한다")
    public void updateUserTest() {
        // given
        User user = new User("userId", "password", "name", "email@example.com");
        User other = new User("userId", "password", "other", "other@example.com");

        // when
        User updatedUser = user.update(other);

        // then
        assertThat(updatedUser.getName()).isEqualTo(other.getName());
        assertThat(updatedUser.getEmail()).isEqualTo(other.getEmail());
    }

    @Test
    @DisplayName("업데이트 시 변경할 유저의 유저 아이디가 일치하지 않으면 예외를 반환한다")
    public void updateUserIdIncorrectTest() {
        // given
        User user = new User("userId", "password", "name", "email@example.com");
        User other = new User("otherId", "password", "other", "other@example.com");

        // when
        CustomException exception = assertThrows(CustomException.class, () -> user.update(other));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.INCORRECT_USER.getMessage());
    }

    @Test
    @DisplayName("업데이트 시 변경할 유저의 비밀번호가 일치하지 않으면 예외를 반환한다")
    public void updateUserPasswordIncorrectTest() {
        // given
        User user = new User("userId", "password", "name", "email@example.com");
        User other = new User("userId", "secret", "other", "other@example.com");

        // when
        CustomException exception = assertThrows(CustomException.class, () -> user.update(other));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.INCORRECT_USER.getMessage());
    }

}
