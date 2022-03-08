package com.kakao.cafe.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("아이디, 비밀번호, 이름, 이메일 중 하나의 값이라도 비어있을 경우 객체 생성이 불가능하고 예외가 발생한다.")
    void construct() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, "1234", "테스트1", "test1@gmail.com"));
        assertThrows(IllegalArgumentException.class, () -> new User("test2", null, "테스트2", "test2@gmail.com"));
        assertThrows(IllegalArgumentException.class, () -> new User("test3", "1234", null, "test3@gmail.com"));
        assertThrows(IllegalArgumentException.class, () -> new User("test4", "1234", "테스트4", null));
    }
}
