package com.kakao.cafe.integration.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.session.SessionUser;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.HandlerInterceptor;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@AutoConfigureMockMvc
@Sql("classpath:/schema-h2.sql")
@DisplayName("UserController 통합 테스트")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserSetUp userSetUp;

    @MockBean
    private HandlerInterceptor interceptor;

    private MockHttpSession session;
    private User user;
    private UserResponse userResponse;
    private SessionUser sessionUser;

    @BeforeEach
    public void setUp() throws Exception {
        given(interceptor.preHandle(any(), any(), any())).willReturn(true);

        user = new User("userId", "userPassword", "userName", "user@example.com");
        userResponse = new UserResponse(1, "userId", "userPassword", "userName",
            "user@example.com");
        sessionUser = new SessionUser(1, "userId", "userPassword", "userName",
            "user@example.com");

        session = new MockHttpSession();
        session.setAttribute(SessionUser.SESSION_KEY, sessionUser);
    }

    @AfterEach
    public void exit() {
        userSetUp.rollback();
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
    public void listUserTest() throws Exception {
        // given
        userSetUp.saveUser(user);

        // when
        ResultActions actions = performGet("/users");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("users", List.of(userResponse)))
            .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("유저 아이디로 유저를 조회한다")
    public void showUserTest() throws Exception {
        // given
        userSetUp.saveUser(user);

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
    @DisplayName("유저 회원 가입을 진행한다.")
    public void createUserTest() throws Exception {
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
        userSetUp.saveUser(user);

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
        // when
        ResultActions actions = mockMvc.perform(get("/users/userId")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

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
        userSetUp.saveUser(user);

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
        userSetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(put("/users/userId")
            .session(session)
            .param("password", "userPassword")
            .param("name", "otherPassword")
            .param("email", "other@example.com")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("유저 정보 업데이트 중 유저 아이디가 존재하지 않을 경우 에러 페이지를 출력한다")
    public void updateUserPasswordTest() throws Exception {
        // given
        User other = new User("otherId", "userPassword", "otherName", "other@example.com");

        userSetUp.saveUser(other);

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
    @DisplayName("유저 정보 업데이트 중 비밀번호가 일치하지 않을 경우 에러 페이지를 출력한다")
    public void updateUserPasswordIncorrectTest() throws Exception {
        // given
        userSetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(put("/users/userId")
            .session(session)
            .param("password", "otherPassword")
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
        session.removeAttribute(SessionUser.SESSION_KEY);

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
        session.removeAttribute(SessionUser.SESSION_KEY);

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

