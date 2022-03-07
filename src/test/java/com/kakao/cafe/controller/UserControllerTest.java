package com.kakao.cafe.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kakao.cafe.domain.user.dto.UserProfileDto;
import com.kakao.cafe.service.UserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

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
            .andExpect(view().name("/list"));
    }

    @DisplayName("존재하는 userId를 대상으로 /users/{userId}를 호출할 경우, 프로필 view가 반환된다.")
    @Test
    void get_userProfile() throws Exception {
        // given
        given(userService.findUserProfileByUserId("123"))
            .willReturn(new UserProfileDto("name", "e@mail"));

        mvc.perform(get("/users/123"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("userProfile"))
            .andExpect(view().name("/user/profile"));
    }

    @DisplayName("id check를 위한 GET요청이 올 경우, /user/passwordCheck view가 반환된다.")
    @Test
    void get_check() throws Exception {
        mvc.perform(get("/users/123/check"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("userId"))
            .andExpect(view().name("/user/passwordCheck"));
    }
}
