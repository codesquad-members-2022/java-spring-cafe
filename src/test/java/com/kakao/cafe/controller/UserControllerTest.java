package com.kakao.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    private MockHttpSession httpSession;

    private User user;

    @BeforeEach
    void init() {
        user = new User("lucid", "1234", "jh", "lucid@lucid");
        userService.save(user);
        httpSession = new MockHttpSession();
        httpSession.setAttribute("sessionUser", user);
    }

    @DisplayName("users에 form 데이터와 함께 post 요청을 하면 /로 리다이렉트된다.")
    @Test
    void post_users() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("userId", "test")
                .param("password", "0000")
                .param("name", "jh")
                .param("email", "test@test"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    }

    @DisplayName("세션에 User가 없는 상황에서, users에 GET 요청을 하면 [로그인 후 확인할 수 있습니다]는 메시지와 에러페이지가 반환된다.")
    @Test
    void get_users_with_no_session() throws Exception {
        mvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "[ERROR] 로그인 후 확인할 수 있습니다."))
            .andExpect(view().name("error/error"));
    }

    @DisplayName("세션에 User가 존재하는 상황에서, users에 GET 요청을 하면 list view가 반환된다.")
    @Test
    void get_users() throws Exception {
        mvc.perform(get("/users")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("users"))
            .andExpect(view().name("list"));
    }

    @DisplayName("존재하는 userId를 대상으로 /users/{userId}를 호출할 경우, 프로필 뷰가 반환된다.")
    @Test
    void get_userProfile() throws Exception {
        mvc.perform(get("/users/lucid"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/profile"));
    }

    @DisplayName("존재하지 않는 userId를 대상으로 /users/{userId}를 호출할 경우, 존재하지 않는 멤버라는 에러페이지가 반환된다.")
    @Test
    void get_userProfile_error() throws Exception {
        mvc.perform(get("/users/33"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "[ERROR] 존재하지 않는 멤버입니다."))
            .andExpect(view().name("error/error"));
    }

    @DisplayName("존재하는 id를 대상으로, 동일한 세션일 경우에만 비밀번호 변경 폼을 받는다.")
    @Test
    void get_passwordForm() throws Exception {
        mvc.perform(get("/users/lucid/check")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("user/passwordCheck"));
    }

    @DisplayName("존재하는 id를 대상으로, 로그인이 안된 상태일 경우 경우 에러페이지가 반환된다.")
    @Test
    void get_passwordForm_without_session_fail() throws Exception {
        mvc.perform(get("/users/lucid/check"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "[ERROR] 로그인 후 확인할 수 있습니다."))
            .andExpect(view().name("error/error"));
    }

    @DisplayName("존재하는 id를 대상으로, 다른 세션 접근일 경우 자신의 계정만 수정할 수 있다는 에러페이지가 반환된다.")
    @Test
    void get_passwordForm_another_session_info_fail() throws Exception {
        httpSession.setAttribute("sessionUser", new User("tesla", "0000", "aaaa", "aa@bb"));
        mvc.perform(get("/users/lucid/check")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "[ERROR] 자신의 계정만 수정할 수 있습니다."))
            .andExpect(view().name("error/error"));
    }

    @DisplayName("존재하는 id를 대상으로 edit Post 요청 시, session이 존재하고 정상 비밀번호를 입력하면 updateForm이 반환된다.")
    @Test
    void post_edit() throws Exception {
        mvc.perform(post("/users/lucid/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("password", "1234")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("user/updateForm"));
    }

    @DisplayName("존재하는 id를 대상으로 edit Post 요청 시, session이 존재하지 않는다면 로그인 되어있지 않다는 에러페이지가 반환된다.")
    @Test
    void post_edit_no_session_fail() throws Exception {
        mvc.perform(post("/users/lucid/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("password", "1234"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "[ERROR] 로그인 후 확인할 수 있습니다."))
            .andExpect(view().name("error/error"));
    }

    @DisplayName("존재하는 id를 대상으로 edit Post 요청 시 틀린 비밀번호를 입력하면 에러페이지가 반환된다.")
    @Test
    void post_edit_not_matched_error() throws Exception {
        mvc.perform(post("/users/lucid/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("password", "0000")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "[ERROR] 비밀번호가 틀렸습니다."))
            .andExpect(view().name("error/error"));
    }

    @DisplayName("존재하지 않는 id를 대상으로 edit Post 요청이 올 경우, 우선 세션이 없다면 로그인을 먼저 하라는 에러페이지가 반환된다.")
    @Test
    void post_edit_error_without_session_and_no_exist_account() throws Exception {
        mvc.perform(post("/users/123/form"))
            .andExpect(status().isOk())
            .andExpect(view().name("error/error"))
            .andExpect(model().attribute("message", "[ERROR] 로그인 후 확인할 수 있습니다."));
    }

    @DisplayName("존재하지 않는 id를 대상으로 edit Post 요청이 올 경우, 세션이 있다면 존재하지 않는다는 에러페이지가 반환된다.")
    @Test
    void post_edit_error_with_session() throws Exception {
        mvc.perform(post("/users/123/form")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("error/error"))
            .andExpect(model().attribute("message", "[ERROR] 존재하지 않는 멤버입니다."));
    }

    @AfterEach
    void finish() {
        userService.clear();
        httpSession.clearAttributes();
    }
}
