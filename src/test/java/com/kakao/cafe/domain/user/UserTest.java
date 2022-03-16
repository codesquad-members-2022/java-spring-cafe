package com.kakao.cafe.domain.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void init() {
        // given
        user = new User("lucid", "1234", "leejohy@naver.com", "leejo");
    }

    @DisplayName("User 객체가 정상적으로 생성된다.")
    @Test
    void makeUser() {
        // then
        assertThat(user).isInstanceOf(User.class);
    }

    @DisplayName("User 객체를 전달하여, 이름이 일치하면 true를, 아니면 false를 반환한다.")
    @Test
    void check_if_the_name_is_the_same_by_user_instance() {
        // given
        User tmpUser1 = new User("lucid", "2345", "lee@naver.com", "lejo");
        User tmpUser2 = new User("tesla", "2345", "lee@naver.com", "lejo");

        // when
        boolean result1 = user.checkIfTheIDIsTheSame(tmpUser1);
        boolean result2 = user.checkIfTheIDIsTheSame(tmpUser2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("Userid String을 전달하여, 이름이 일치하면 true를, 아니면 false를 반환한다.")
    @Test
    void check_if_the_name_is_the_same_by_userid_string() {
        // when
        boolean result1 = user.isYourId("lucid");
        boolean result2 = user.isYourId("tesla");

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("비밀번호가 일치하면 true를, 아니면 false를 반환한다.")
    @Test
    void check_if_the_passwords_match() {
        // when
        boolean result1 = user.isYourPassword("1234");
        boolean result2 = user.isYourPassword("3333");

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }
}
