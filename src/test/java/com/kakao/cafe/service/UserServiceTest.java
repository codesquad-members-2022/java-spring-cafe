package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.UserException;
import com.kakao.cafe.repository.MemoryUserRepository;

class UserServiceTest {
    MemoryUserRepository userRepository;
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("회원가입을 하면 회원 정보가 MemoryUserRepository에 저장된다")
    void join_success() {
        User user1 = new User("BC", "BC@gmail.com", "1234");
        userService.join(user1);

        User user = userService.findByNickname("BC");
        assertThat(user.getNickname()).isEqualTo(user1.getNickname());
        assertThat(user.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    @DisplayName("회원가입시 이미 등록되어있는 닉네임 또는 이메일을 입력하면 예외를 발생시킨다")
    void join_validateUniqueNickname() {
        User user1 = new User("BC", "BC@gmail.com", "1234");
        userService.join(user1);

        User user2 = new User("BC", "WJJ@gmail.com", "1g2345");
        User user3 = new User("CAPI", "BC@gmail.com", "1234");

        assertThatThrownBy(() -> userService.join(user2))
            .isInstanceOf(UserException.class)
            .hasMessage(ErrorCode.EXISTING_NICKNAME.message);

        assertThatThrownBy(() -> userService.join(user3))
            .isInstanceOf(UserException.class)
            .hasMessage(ErrorCode.EXISTING_EMAIL.message);
    }

    @Test
    @DisplayName("회원가입을 하면 id가 1부터 오름차순으로 배정된다")
    void join_assign_id() {
        User user1 = new User("bc", "HARRY@gmail.com", "1234");
        userService.join(user1);

        User user2 = new User("BBBB", "BC@gmail.com", "1234");
        userService.join(user2);

        assertThat(user1.matchesId(1)).isTrue();
        assertThat(user2.matchesId(2)).isTrue();
    }
}
