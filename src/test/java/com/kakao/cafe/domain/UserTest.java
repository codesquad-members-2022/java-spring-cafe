package com.kakao.cafe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User 도메인 단위 테스트")
public class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("사용자 정보 중 이름(name)과 이메일(email)을 수정할 수 있다.")
    @Test
    void 사용자_정보_수정() {
        // given
        User updatedUser = new User("ikjo", "1234", "ikjo", "auddlr100@gmail.com");

        // when
        user.update(updatedUser);

        // then
        assertThat(user.getName()).isEqualTo("ikjo");
        assertThat(user.getEmail()).isEqualTo("auddlr100@gmail.com");
    }


    @DisplayName("사용자 정보 중 ID 값(userId)이 ikjo와 일치한다.")
    @Test
    void 사용자_ID_일치() {
        // when
        boolean result = user.hasSameUserId("ikjo");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("사용자 정보 중 비밀번호 값(password)이 1234와 일치한다.")
    @Test
    void 사용자_비밀번호_일치() {
        // when
        boolean result = user.hasSamePassword("1234");

        // then
        assertThat(result).isTrue();
    }
}
