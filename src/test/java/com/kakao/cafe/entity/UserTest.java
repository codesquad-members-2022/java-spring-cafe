package com.kakao.cafe.entity;

import com.kakao.cafe.domain.User;
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

    @Test
    @DisplayName("전달받은 아이디와, User객체의 아이디가 일치한다면 true를 리턴한다.")
    void is_same_user_id() {
        //given
        String userId = "test1";
        User user = new User("test1", "1234", "테스트1", "test1@gmail.com");

        //when
        assertTrue(user.isSameUser(userId));
    }

    @Test
    @DisplayName("전달받은 아이디와, User객체의 아이디가 다르다면 false를 리턴한다.")
    void is_differ_user_id() {
        //given
        String userId = "test2255";
        User user = new User("test1", "1234", "테스트1", "test1@gmail.com");

        //when
        assertFalse(user.isSameUser(userId));
    }
}
