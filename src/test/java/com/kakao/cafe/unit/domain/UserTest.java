package com.kakao.cafe.unit.domain;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User 도메인 단위 테스트")
public class UserTest {

    @Test
    @DisplayName("유저 아이디와 비밀번호가 일치하면 유저 객체의 속성을 변경한다")
    public void updateUserTest() {
        // given
        User user = new User("userId", "userPassword", "userName", "user@example.com");
        User other = new User("userId", "userPassword", "otherName", "other@example.com");

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
        User user = new User("userId", "userPassword", "userName", "user@example.com");
        User other = new User("otherId", "userPassword", "otherName", "other@example.com");

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
        User user = new User("userId", "userPassword", "userName", "user@example.com");
        User other = new User("userId", "otherPassword", "userName", "user@example.com");

        // when
        Throwable throwable = catchThrowable(() -> user.update(other));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

}
