package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogoutController.class)
public class LogoutControllerUnitTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    LoginService service;

    MockHttpSession session;
    
    User user;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @DisplayName("로그아웃 요청이 들어오면 세션에 저장된 사용자 정보를 삭제하고 질문 글 목록으로 리다이렉트시킨다.")
    @Test
    void loginSuccess() throws Exception {
        // given
        user = new User(1, "userId", "password", "name", "email");
        session.setAttribute("user", user);

        // when
        mvc.perform(get("/logout").session(session))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/")
                );

        // then
        User sessionUser = (User) session.getAttribute("userInfo");
        assertThat(sessionUser).isNull();
    }
}
