package com.kakao.cafe.unit.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.controller.UserController;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

    private static final Integer USER_NUM = 1;
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NAME = "user";
    private static final String USER_EMAIL = "user@example.com";

    private static final String OTHER_ID = "otherId";
    private static final String OTHER_PASSWORD = "secret";
    private static final String OTHER_NAME = "other";
    private static final String OTHER_EMAIL = "other@example.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL);
    }

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void listUsersTest() throws Exception {
        // given
        user.setUserNum(USER_NUM);
        List<User> users = List.of(user);

        given(userService.findUsers())
            .willReturn(users);

        // when
        ResultActions actions = mockMvc.perform(get("/users")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("users", users))
            .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("유저 아이디로 유저를 조회한다")
    public void showUserTest() throws Exception {
        // given
        given(userService.findUser(any()))
            .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(get("/users/" + user.getUserId())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("user", user))
            .andExpect(view().name("user/profile"));
    }

    @Test
    @DisplayName("유저 회원 가입 화면을 보여준다")
    public void createUserFormTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/users/form")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("유저 회원 가입을 진행한다")
    public void createUserTest() throws Exception {
        // given
        given(userService.register(any()))
            .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(post("/users")
            .param("userId", USER_ID)
            .param("password", USER_PASSWORD)
            .param("name", USER_NAME)
            .param("email", USER_EMAIL)
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(model().attribute("user", allOf(
                hasProperty("userId", is(USER_ID)),
                hasProperty("password", is(USER_PASSWORD)),
                hasProperty("name", is(USER_NAME)),
                hasProperty("email", is(USER_EMAIL))
            )))
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저 회원가입할 때 이미 있는 유저 아이디면 예외 페이지로 이동한다")
    public void createUserValidateTest() throws Exception {
        // given
        CustomException exception = new CustomException(ErrorCode.DUPLICATE_USER);

        given(userService.register(any()))
            .willThrow(exception);

        // when
        ResultActions actions = mockMvc.perform(post("/users")
            .param("userId", USER_ID)
            .param("password", USER_PASSWORD)
            .param("name", USER_NAME)
            .param("email", USER_EMAIL)
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", exception.getErrorCode().getHttpStatus()))
            .andExpect(model().attribute("message", exception.getErrorCode().getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조회하면 예외 페이지로 이동한다")
    public void showUserValidateTest() throws Exception {
        // given
        CustomException exception = new CustomException(ErrorCode.USER_NOT_FOUND);

        given(userService.findUser(any()))
            .willThrow(exception);

        // when
        ResultActions actions = mockMvc.perform(get("/users/userId")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", exception.getErrorCode().getHttpStatus()))
            .andExpect(model().attribute("message", exception.getErrorCode().getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저 아이디로 유저 정보 수정 페이지를 출력한다")
    public void updateUserFormTest() throws Exception {
        // given
        given(userService.findUser(any()))
            .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(get("/users/" + user.getUserId() + "/form")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("user", user))
            .andExpect(view().name("user/update_form"));
    }

    @Test
    @DisplayName("유저 정보 업데이트 후에 메인 페이지로 전환한다")
    public void updateUserTest() throws Exception {
        // given
        User other = new User(user);
        other.setName(OTHER_NAME);
        other.setEmail(OTHER_EMAIL);

        given(userService.updateUser(any()))
            .willReturn(other);

        // when
        ResultActions actions = mockMvc.perform(put("/users/" + user.getUserId())
            .param("password", USER_PASSWORD)
            .param("name", OTHER_NAME)
            .param("email", OTHER_EMAIL)
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(model().attribute("user", allOf(
                hasProperty("name", is(OTHER_NAME)),
                hasProperty("email", is(OTHER_EMAIL))
            )))
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저 정보 변경 시 유저 아이디가 존재하지 않을 경우 에러 페이지를 출력한다")
    public void updateUserIncorrectUserIdTest() throws Exception {
        // given
        CustomException exception = new CustomException(ErrorCode.USER_NOT_FOUND);

        given(userService.updateUser(any()))
            .willThrow(exception);

        // when
        ResultActions actions = mockMvc.perform(put("/users/other")
            .param("password", USER_PASSWORD)
            .param("name", OTHER_NAME)
            .param("email", OTHER_EMAIL)
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", exception.getErrorCode().getHttpStatus()))
            .andExpect(model().attribute("message", exception.getErrorCode().getMessage()))
            .andExpect(view().name("error/index"));
    }


    @Test
    @DisplayName("유저 정보 변경 시 아이디 혹은 비밀번호가 일치하지 않을 경우 에러 페이지를 출력한다")
    public void updateUserIncorrectPasswordTest() throws Exception {
        // given
        CustomException exception = new CustomException(ErrorCode.INCORRECT_USER);

        given(userService.updateUser(any()))
            .willThrow(exception);

        // when
        ResultActions actions = mockMvc.perform(put("/users/userId")
            .param("password", OTHER_PASSWORD)
            .param("name", OTHER_NAME)
            .param("email", OTHER_EMAIL)
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", exception.getErrorCode().getHttpStatus()))
            .andExpect(model().attribute("message", exception.getErrorCode().getMessage()))
            .andExpect(view().name("error/index"));
    }

}

