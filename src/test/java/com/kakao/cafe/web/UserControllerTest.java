package com.kakao.cafe.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserListDto;
import com.kakao.cafe.web.dto.UserLoginFormDto;
import com.kakao.cafe.web.dto.UserProfileDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;
import com.kakao.cafe.web.dto.UserUpdateFormDto;
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
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private MockHttpSession httpSession;

    private User user;

    @BeforeEach
    void setup() {
        httpSession = new MockHttpSession();
        httpSession.setAttribute("sessionedUserId", "id1");
        user = new User.UserBuilder("id1", "pw1")
            .setName("name1")
            .setEmail("1@1.com")
            .build();
    }

    @Test
    @DisplayName("회원가입이 수행된다.")
    void 회원가입_동작_테스트() throws Exception {
        // given
        UserRegisterFormDto userRegisterFormDto = new UserRegisterFormDto("id1", "pw1",
            "name1", "1@1.com");
        String content = objectMapper.writeValueAsString(userRegisterFormDto);

        doNothing().when(userService)
            .register(userRegisterFormDto);

        // then
        mvc.perform(post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(redirectedUrl("/users"));
    }

    @Test
    @DisplayName("모든 회원의 목록을 조회한다.")
    void 회원_목록_출력_테스트() throws Exception {
        // given
        UserListDto userListDto1 = new UserListDto(user, 1);

        given(userService.showAll())
            .willReturn(List.of(userListDto1));

        // when
        ResultActions resultActions = mvc.perform(get("/users"));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(model().attribute("users", List.of(userListDto1)))
            .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("아이디를 통해 회원의 프로필을 조회한다.")
    void 회원_프로필_조회_테스트() throws Exception {
        // given
        UserProfileDto userProfileDto = new UserProfileDto(user);

        given(userService.showOne("id1"))
            .willReturn(userProfileDto);

        // when
        ResultActions resultActions = mvc.perform(get("/users/" + userProfileDto.getUserId()));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(model().attribute("userId", userProfileDto.getUserId()))
            .andExpect(model().attribute("email", userProfileDto.getEmail()))
            .andExpect(view().name("user/profile"));
    }

    @Test
    @DisplayName("로그인이 수행된다.")
    void 로그인_수행_테스트() throws Exception {
        // given
        UserLoginFormDto userLoginFormDto = new UserLoginFormDto("id1", "pw1");
        String content = objectMapper.writeValueAsString(userLoginFormDto);

        given(userService.login(userLoginFormDto))
            .willReturn("id1");

        // when
        ResultActions resultActions = mvc.perform(post("/users/login")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("로그인한 사용자는 자신의 정보 수정 화면에 접근할 수 있다.")
    void 사용자_수정_화면_접근_테스트() throws Exception {
        // given
        String properUserId = "id1";

        // when
        ResultActions resultAction = mvc.perform(get("/users/" + properUserId + "/form")
            .session(httpSession));

        // then
        resultAction.andExpect(status().isOk())
            .andExpect(model().attribute("userId", properUserId))
            .andExpect(view().name("user/updateForm"));
    }

    @Test
    @DisplayName("로그인 하지 않은 사용자는 사용자 수정 화면에 접근할 수 없다.")
    void 사용자_수정_화면_예외_테스트() throws Exception {
        // given
        httpSession.invalidate();
        String properUserId = "id1";

        // then
        assertThatThrownBy(
            () -> mvc.perform(get("/users/" + properUserId + "/form")
                    .session(httpSession))
                .andExpect(status().isOk()))
            .hasCause(new IllegalStateException("로그인이 필요합니다."));
    }

    @Test
    @DisplayName("로그인한 사용자는 자신의 정보를 수정할 수 있다.")
    void 사용자_정보_수정_테스트() throws Exception {
        // given
        UserUpdateFormDto userUpdateFormDto = new UserUpdateFormDto("id1", "pw1", "PW1", "name1",
            "1@1.com");
        doNothing().when(userService)
            .modify(userUpdateFormDto);

        // when
        ResultActions resultAction = mvc.perform(post("/users/id1/update")
            .session(httpSession));

        // then
        resultAction.andExpect(redirectedUrl("/users"));
    }

    @Test
    @DisplayName("로그인 하지 않은 사용자는 사용자 정보를 수정할 수 없다.")
    void 비로그인사용자_정보_수정_예외_테스트() {
        // given
        httpSession.invalidate();
        UserUpdateFormDto userUpdateFormDto = new UserUpdateFormDto("id1", "pw1", "PW1", "name1",
            "1@1.com");
        doNothing().when(userService)
            .modify(userUpdateFormDto);

        // then
        assertThatThrownBy(
            () -> mvc.perform(post("/users/id1/update")
                    .session(httpSession))
                .andExpect(status().isOk()))
            .hasCause(new IllegalStateException("로그인이 필요합니다."));
    }

    @Test
    @DisplayName("로그인한 사용자는 다른 사용자 정보를 수정할 수 없다.")
    void 로그인사용자_다른사용자정보_수정_예외_테스트() {
        // given
        UserUpdateFormDto userUpdateFormDto = new UserUpdateFormDto("id2", "pw2", "PW2", "name2",
            "2@2.com");
        doNothing().when(userService)
            .modify(userUpdateFormDto);

        // then
        assertThatThrownBy(
            () -> mvc.perform(post("/users/id2/update")
                    .session(httpSession))
                .andExpect(status().isOk()))
            .hasCause(new IllegalStateException("접근 권한이 없습니다."));
    }


    @Test
    @DisplayName("로그아웃이 수행된다.")
    void 로그아웃_수행_테스트() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(get("/users/logout"));

        // then
        resultActions.andExpect(redirectedUrl("/"));
    }
}
