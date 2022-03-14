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

    @BeforeEach
    void init() {
        userService.clear();
        userService.save(new User("lucid", "1234", "jh", "lucid@lucid"));
    }

    @DisplayName("users에 post 요청을 하면 /users로 리다이렉트된다.")
    @Test
    void post_users() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("userId", "test")
                .param("password", "0000")
                .param("name", "jh")
                .param("email", "test@test"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users"));
    }

    @DisplayName("users에 GET 요청을 하면 list view가 반환된다.")
    @Test
    void get_users() throws Exception {
        mvc.perform(get("/users"))
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

    @DisplayName("존재하지 않는 userId를 대상으로 /users/{userId}를 호출할 경우, 에러페이지가 반환된다.")
    @Test
    void get_userProfile_error() throws Exception {
        mvc.perform(get("/users/33"))
            .andExpect(status().isOk())
            .andExpect(view().name("/error/error"));
    }

    @DisplayName("id check를 위한 GET요청이 올 경우, /user/passwordCheck view가 반환된다.")
    @Test
    void get_check() throws Exception {
        mvc.perform(get("/users/123/check"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("userId"))
            .andExpect(view().name("user/passwordCheck"));
    }

    @DisplayName("존재하는 id를 대상으로 edit Post 요청 시 정상 비밀번호를 입력하면 updateForm이 반환된다.")
    @Test
    void post_edit() throws Exception {
        mvc.perform(post("/users/lucid/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("password", "1234"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/updateForm"));
    }

    @DisplayName("존재하는 id를 대상으로 edit Post 요청 시 틀린 비밀번호를 입력하면 에러페이지가 반환된다.")
    @Test
    void post_edit_not_matched_error() throws Exception {
        mvc.perform(post("/users/lucid/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("password", "0000"))
            .andExpect(status().isOk())
            .andExpect(view().name("/error/error"));
    }

    @DisplayName("존재하지 않는 id를 대상으로 edit Post 요청이 올 경우, 에러페이지가 반환된다.")
    @Test
    void post_edit_error() throws Exception {
        mvc.perform(post("/users/123/form"))
            .andExpect(status().isOk())
            .andExpect(view().name("/error/error"));
    }

    @AfterEach
    void finish() {
        userService.clear();
    }
}
