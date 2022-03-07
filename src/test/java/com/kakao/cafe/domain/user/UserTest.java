package com.kakao.cafe.domain.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("User 객체가 정상적으로 생성된다.")
    @Test
    void makeUser() {
        // given & when
        User user = new User("lucid", "1234", "leejohy@naver.com", "leejo");

        // then
        assertThat(user).isInstanceOf(User.class);
    }
}
