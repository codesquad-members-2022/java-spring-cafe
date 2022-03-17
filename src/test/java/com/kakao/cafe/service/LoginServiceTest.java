package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.JdbcTemplateUserRepository;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class LoginServiceTest {
    LoginService loginService;
    UserRepository userRepository;
    @Autowired
    DataSource dataSource;

    @BeforeEach
    public void beforeEach() {
        userRepository = new JdbcTemplateUserRepository(dataSource);
        loginService = new LoginService(userRepository);
    }

    @Test
    @DisplayName("로그인에 성공한 경우 해당 유저를 잘 반환하는가")
    void login() {
        User user = loginService.login("user1", "123123a");
        assertThat("user1").isEqualTo(user.getUserId());
    }

    @Test
    @DisplayName("로그인 시 비밀번호가 틀린 경우 예외를 발생시키는가")
    void loginFailByWrongPassword() {
        assertThatThrownBy(() -> loginService.login("user1", "456456b"))
                .hasMessage("비밀번호가 틀립니다.");
    }

    @Test
    @DisplayName("로그인 시 없는 아이디일 경우 예외를 발생시키는가")
    void loginFailByWrongUserId() {
        assertThatThrownBy(() -> loginService.login("nathan", "123123a"))
                .hasMessage("아이디가 없습니다.");
    }
}
