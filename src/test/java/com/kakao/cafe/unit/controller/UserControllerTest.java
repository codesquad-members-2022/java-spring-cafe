package com.kakao.cafe.unit.controller;

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
import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.exception.DuplicateException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserController.class)
@DisplayName("UserController 단위 테스트")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private MockHttpSession session;

    User user;
    UserResponse userResponse;

    @BeforeEach
    public void setUp() {
        user = new User.Builder()
            .userNum(1)
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();

        userResponse = new UserResponse(1, "userId", "userPassword", "userName",
            "user@example.com");

        session = new MockHttpSession();
        session.setAttribute("SESSION_USER", user);
    }

    private ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(get(url)
            .accept(MediaType.TEXT_HTML));
    }

    private ResultActions performGet(String url, MockHttpSession session) throws Exception {
        return mockMvc.perform(get(url)
            .session(session)
            .accept(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void listUsersTest() throws Exception {
        // given
        List<UserResponse> users = List.of(userResponse);

        given(userService.findUsers())
            .willReturn(users);

        System.out.println("users = " + users);

        // when
        ResultActions actions = performGet("/users");

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
            .willReturn(userResponse);

        // when
        ResultActions actions = performGet("/users/userId");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("user", userResponse))
            .andExpect(view().name("user/profile"));
    }

    @Test
    @DisplayName("유저 회원 가입 화면을 보여준다")
    public void createUserFormTest() throws Exception {
        // when
        ResultActions actions = performGet("/users/form");

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("유저 회원 가입을 진행한다")
    public void createUserTest() throws Exception {
        // given
        given(userService.register(any()))
            .willReturn(userResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/users")
            .param("userId", "userId")
            .param("password", "userPassword")
            .param("name", "userName")
            .param("email", "user@example.com")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저 회원가입할 때 이미 있는 유저 아이디면 예외 페이지로 이동한다")
    public void createUserValidateTest() throws Exception {
        // given
        given(userService.register(any()))
            .willThrow(new DuplicateException(ErrorCode.DUPLICATE_USER));

        // when
        ResultActions actions = mockMvc.perform(post("/users")
            .param("userId", "userId")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.DUPLICATE_USER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.DUPLICATE_USER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조회하면 예외 페이지로 이동한다")
    public void showUserValidateTest() throws Exception {
        // given
        given(userService.findUser(any()))
            .willThrow(new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // when
        ResultActions actions = performGet("/users/userId");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.USER_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.USER_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저 아이디로 유저 정보 수정 페이지를 출력한다")
    public void updateUserFormTest() throws Exception {
        // given
        given(userService.findUser(any()))
            .willReturn(userResponse);

        // when
        ResultActions actions = performGet("/users/userId/form", session);

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("user", userResponse))
            .andExpect(view().name("user/update_form"));
    }

    @Test
    @DisplayName("유저 정보 업데이트 후에 메인 페이지로 전환한다")
    public void updateUserTest() throws Exception {
        // given
        UserResponse changedUser = new UserResponse(1, "userId", "userPassword", "otherName",
            "other@example.com");

        given(userService.updateUser(any()))
            .willReturn(changedUser);

        // when
        ResultActions actions = mockMvc.perform(put("/users/userId")
            .session(session)
            .param("password", "userPassword")
            .param("name", "otherName")
            .param("email", "other@example.com")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저 정보 변경 시 유저 아이디가 존재하지 않을 경우 에러 페이지를 출력한다")
    public void updateUserIncorrectUserIdTest() throws Exception {
        // given
        given(userService.updateUser(any()))
            .willThrow(new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // when
        ResultActions actions = mockMvc.perform(put("/users/userId")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.USER_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.USER_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }


    @Test
    @DisplayName("유저 정보 변경 시 아이디 혹은 비밀번호가 일치하지 않을 경우 에러 페이지를 출력한다")
    public void updateUserIncorrectPasswordTest() throws Exception {
        // given
        given(userService.updateUser(any()))
            .willThrow(new InvalidRequestException(ErrorCode.INCORRECT_USER));

        // when
        ResultActions actions = mockMvc.perform(put("/users/userId")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.INCORRECT_USER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INCORRECT_USER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저 정보 업데이트 폼 페이지 요청 시 세션이 존재하지 않으면 에러 페이지를 출력한다.")
    public void formUpdateUserSessionNotFoundTest() throws Exception {
        session.removeAttribute("SESSION_USER");

        // when
        ResultActions actions = mockMvc.perform(get("/users/otherId/form")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.SESSION_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.SESSION_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저 정보 업데이트 폼 페이지 요청 시 세션과 유저 정보가 일치하지 않을 경우 에러 페이지를 출력한다")
    public void formUpdateUserSessionIncorrectTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/users/otherId/form")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.INCORRECT_USER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INCORRECT_USER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저 정보 업데이트 요청 시 세션이 존재하지 않으면 에러 페이지를 출력한다.")
    public void updateUserSessionNotFoundTest() throws Exception {
        session.removeAttribute("SESSION_USER");

        // when
        ResultActions actions = mockMvc.perform(put("/users/otherId")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.SESSION_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.SESSION_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저 정보 업데이트 요청 시 세션과 유저 정보가 일치하지 않을 경우 에러 페이지를 출력한다")
    public void updateUserSessionTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(put("/users/otherId")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.INCORRECT_USER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INCORRECT_USER.getMessage()))
            .andExpect(view().name("error/index"));
    }

}

