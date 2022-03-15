package com.kakao.cafe.unit.domain;

import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User 도메인 단위 테스트")
public class UserTest {

    @Test
    @DisplayName("유저 아이디와 비밀번호가 일치하면 유저 객체의 속성을 변경한다")
    public void updateUserTest() {
        // given
        User user = new User("userId", "userPassword", "userName", "user@example.com");

        // when
        User updatedUser = user.update("otherName", "other@example.com");

        // then
        then(updatedUser.getName()).isEqualTo("otherName");
        then(updatedUser.getEmail()).isEqualTo("other@example.com");
    }

}
