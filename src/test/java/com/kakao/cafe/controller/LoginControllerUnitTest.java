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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.kakao.cafe.util.Convertor.convertToMultiValueMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerUnitTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    LoginService service;

    MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @DisplayName("로그인 요청 정보의 비밀번호와 실제 사용자의 비밀번호가 일치하면 세션에 사용자 정보를 저장한 후 사용자 목록 페이지로 이동한다.")
    @Test
    void loginSuccess() throws Exception {
        // given
        LoginParam loginParam = new LoginParam("userId", "password");
        User user = new User(1, "userId", "password", "name", "email");
        given(service.checkInfo(ArgumentMatchers.refEq(loginParam))).willReturn(user);

        // when
        mvc.perform(post("/login")
                        .params(convertToMultiValueMap(loginParam)).session(session))

                // then
                .andExpectAll(
                        request().sessionAttribute("userInfo", user),
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        verify(service).checkInfo(ArgumentMatchers.refEq(loginParam));
    }

    @DisplayName("로그인 요청 정보의 비밀번호와 실제 사용자의 비밀번호가 일치하지 않으면 user/login_failed.html 을 읽어온다.")
    @Test
    void loginFail() throws Exception {
        // given
        LoginParam loginParam = new LoginParam("userId", "Inconsistency");
        User user = new User(1, "userId", "password", "name", "email");
        given(service.checkInfo(ArgumentMatchers.refEq(loginParam))).willReturn(user);

        // when
        mvc.perform(post("/login")
                        .params(convertToMultiValueMap(loginParam)).session(session))

                // then
                .andExpectAll(
                        request().sessionAttributeDoesNotExist("userInfo"),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        view().name("user/login_failed"),
                        status().isOk()
                );

        verify(service).checkInfo(ArgumentMatchers.refEq(loginParam));
    }
}
