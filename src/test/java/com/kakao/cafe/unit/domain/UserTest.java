package com.kakao.cafe.unit.domain;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    @DisplayName("유저 아이디와 비밀번호가 일치하면 엔티티의 속성을 변경한다")
    public void updateUserTest() {
        // given
        User user = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();

        User other = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("otherName")
            .email("other@example.com")
            .build();

        // when
        User updatedUser = user.update(other);

        // then
        then(updatedUser.getName()).isEqualTo(other.getName());
        then(updatedUser.getEmail()).isEqualTo(other.getEmail());
    }

    @Test
    @DisplayName("업데이트 시 변경할 유저의 유저 아이디가 일치하지 않으면 예외를 반환한다")
    public void updateUserIdIncorrectTest() {
        // given
        User user = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();

        User other = new User.Builder()
            .userId("otherId")
            .password("userPassword")
            .name("otherName")
            .email("other@example.com")
            .build();

        // when
        Throwable throwable = catchThrowable(() -> user.update(other));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

    @Test
    @DisplayName("업데이트 시 변경할 유저의 비밀번호가 일치하지 않으면 예외를 반환한다")
    public void updateUserPasswordIncorrectTest() {
        // given
        User user = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();

        User other = new User.Builder()
            .userId("userId")
            .password("otherPassword")
            .name("otherName")
            .email("other@example.com")
            .build();

        // when
        Throwable throwable = catchThrowable(() -> user.update(other));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

}
