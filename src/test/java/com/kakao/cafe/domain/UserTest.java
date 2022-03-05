package com.kakao.cafe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User userInformation;

    @BeforeEach
    void setup() {
        userInformation = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("사용자 정보 중 ID 값(userId)이 ikjo와 일치한다.")
    @Test
    void 사용자_ID_일치() {
        // when
        boolean result = userInformation.hasSameUserId("ikjo");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("사용자 정보 중 비밀번호 값(password)이 1234와 일치한다.")
    @Test
    void 사용자_비밀번호_일치() {
        // when
        boolean result = userInformation.hasSamePassword("1234");

        // then
        assertThat(result).isTrue();
    }
}
