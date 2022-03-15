package com.kakao.cafe.web;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserJoinDto;
import com.kakao.cafe.web.dto.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService service;
    User user;

    @BeforeEach
    void setUp() {
        user = new User("testUserId", "testPassword", "testName", "testEmail");
    }

    @Test
    @DisplayName("유저 회원가입 폼을 보여준다")
    void joinForm() throws Exception {
        //when 실행
        ResultActions actions = mockMvc.perform(get("/user/form")
                .accept(MediaType.TEXT_HTML));

        //then 검증
        actions.andExpect(status().isOk())
                .andExpect(view().name("user/form"));
    }
    @Test
    void join() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(post("/user/form")
                .param("userId", "test")
                .param("password", "1234")
                .param("name", "test")
                .param("email", "test@naver.com")
                .accept(MediaType.TEXT_HTML_VALUE));
        //then
        actions.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void list() throws Exception {
        List<User> users = List.of(this.user);
        given(service.findUsers()).willReturn(users);
        //when
        ResultActions actions = mockMvc.perform(get("/user/list"));
        //then
        actions.andExpect(status().isOk())
                .andExpect(model().attribute("users", users))
                .andExpect(view().name("user/list"));

    }

    @Test
    void profile() throws Exception {
        given(service.findUser(user.getUserId())).willReturn(user);
        //when
        ResultActions actions = mockMvc.perform(get("/user/" + user.getUserId()));
        //given
        actions.andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("user/profile"));
    }

}
