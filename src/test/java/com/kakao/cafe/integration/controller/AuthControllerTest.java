package com.kakao.cafe.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AuthController 통합 테스트")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserSetUp userSetUp;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();
    }

    @Test
    @DisplayName("유저 로그인 폼 페이지를 보여준다")
    public void formLoginTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/login/form")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("user/login"));
    }

    @Test
    @DisplayName("유저가 로그인 정보를 입력하고 로그인에 성공한다")
    public void loginTest() throws Exception {
        // given
        userSetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(post("/login")
            .param("userId", "userId")
            .param("password", "userPassword")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저가 존재하지 않는 유저 아이디를 입력하고 로그인에 실패한다")
    public void loginUserNotFoundTest() throws Exception {
        // given
        userSetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(post("/login")
            .param("userId", "otherId")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.USER_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.USER_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저가 일치하지 않는 비밀번호를 입력하고 로그인에 실패한다")
    public void loginIncorrectUserTest() throws Exception {
        // given
        userSetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(post("/login")
            .param("userId", "userId")
            .param("password", "otherPassword")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.INCORRECT_USER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INCORRECT_USER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저가 로그아웃한다")
    public void logOutTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/logout")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/users"));
    }
}
