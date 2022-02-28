package com.kakao.cafe.domain.users;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("User 객체가 정상적으로 생성된다.")
    @Test
    void makeUser() {
        // given & when
        User user = User.builder()
            .userId("lucid")
            .password("1234")
            .email("leejohy@naver.com")
            .name("leejo")
            .build();

        // then
        assertThat(user).isInstanceOf(User.class);
    }

}