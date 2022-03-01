package com.kakao.cafe.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.controller.UserController;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void getUsersTest() throws Exception {
        // given
        List<User> users = List.of(new User("userId", "password", "name", "email@example.com"));

        given(userService.findUsers())
            .willReturn(users);

        // when
        ResultActions actions = mockMvc.perform(get("/users")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("users", users))
            .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("유저 아이디로 유저를 조회한다")
    public void getUserTest() throws Exception {
        // given
        User user = new User("userId", "password", "name", "email@example.com");

        given(userService.findUser("userId"))
            .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(get("/users/" + user.getUserId())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("user", user))
            .andExpect(view().name("user/profile"));
    }

    @Test
    @DisplayName("유저 회원 가입 화면을 보여준다")
    public void getRegisterTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/users/register")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("유저 회원 가입을 진행한다")
    public void postRegisterTest() throws Exception {
        // given
        User user = new User("userId", "password", "name", "email@example.com");

        given(userService.register(user))
            .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(post("/users/register")
            .param("userId", user.getUserId())
            .param("password", user.getPassword())
            .param("name", user.getName())
            .param("email", user.getEmail())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions
            .andExpect(model().attribute("user", user))
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저 회원가입할 때 이미 있는 유저 아이디면 예외 페이지로 이동한다")
    public void postRegisterValidateTest() throws Exception {
        // given
        CustomException exception = new CustomException(ErrorCode.DUPLICATE_USER);

        given(userService.register(any()))
            .willThrow(exception);

        // when
        ResultActions actions = mockMvc.perform(post("/users/register")
            .param("userId", "userId")
            .param("password", "password")
            .param("name", "name")
            .param("email", "email@example.com")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("status", exception.getErrorCode().getHttpStatus()))
            .andExpect(model().attribute("message", exception.getErrorCode().getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조회하면 예외 페이지로 이동한다")
    public void findUserValidateTest() throws Exception {
        // given
        CustomException exception = new CustomException(ErrorCode.USER_NOT_FOUND);

        given(userService.findUser(any()))
            .willThrow(exception);

        // when
        ResultActions actions = mockMvc.perform(get("/users/userId")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("status", exception.getErrorCode().getHttpStatus()))
            .andExpect(model().attribute("message", exception.getErrorCode().getMessage()))
            .andExpect(view().name("error/index"));
    }


}

