package com.kakao.cafe.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserSetUp userSetUp;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("userId", "password", "name", "email@example.com");
    }

    @AfterEach
    public void exit() {
        userSetUp.rollback();
    }

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void listUserTest() throws Exception {
        // given
        User savedUser = userSetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(get("/users")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("users", List.of(savedUser)))
            .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("유저 아이디로 유저를 조회한다")
    public void showUserTest() throws Exception {
        // given
        User savedUser = userSetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(get("/users/" + user.getUserId())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("user", savedUser))
            .andExpect(view().name("user/profile"));
    }

    @Test
    @DisplayName("유저 회원 가입 화면을 보여준다")
    public void createUserFormTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/users/form")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("유저 회원 가입을 진행한다.")
    public void createUserTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(post("/users")
            .param("userId", user.getUserId())
            .param("password", user.getPassword())
            .param("name", user.getName())
            .param("email", user.getEmail())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("user", user))
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저 회원가입할 때 이미 있는 유저 아이디면 예외 페이지로 이동한다")
    public void createUserValidateTest() throws Exception {
        // given
        User savedUser = userSetUp.saveUser(this.user);

        // when
        ResultActions actions = mockMvc.perform(post("/users")
            .param("userId", user.getUserId())
            .param("password", "secret")
            .param("name", "other")
            .param("email", "other@example.com")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("status", ErrorCode.DUPLICATE_USER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.DUPLICATE_USER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조회하면 예외 페이지로 이동한다")
    public void showUserValidateTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/users/userId")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("status", ErrorCode.USER_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.USER_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }
}

