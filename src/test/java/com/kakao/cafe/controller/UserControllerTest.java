package com.kakao.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("users에 post 요청을 하면 /users로 리다이렉트된다.")
    @Test
    void post_users() throws Exception {
        mvc.perform(post("/users"))
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
}