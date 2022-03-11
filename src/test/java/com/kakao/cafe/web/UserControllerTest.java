package com.kakao.cafe.web;

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
import com.kakao.cafe.web.dto.UserProfileDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;
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
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입이 수행된다.")
    void 회원가입_동작_테스트() throws Exception {
        // given
        UserRegisterFormDto userRegisterFormDto = new UserRegisterFormDto("testId1", "testPw1",
            "testName1", "test@test.com");
        String content = objectMapper.writeValueAsString(userRegisterFormDto);

        doNothing().when(userService)
            .register(userRegisterFormDto);

        // then
        mvc.perform(post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(redirectedUrl("users"));
    }

    @Test
    @DisplayName("모든 회원의 목록을 조회한다.")
    void 회원_목록_출력_테스트() throws Exception {
        // given
        User user1 = new User.UserBuilder("testId1", "testPw1")
            .setName("testName")
            .setEmail("test@test.com")
            .build();
        UserListDto userListDto1 = new UserListDto(user1);

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
        User user1 = new User.UserBuilder("testId1", "testPw1")
            .setName("testName")
            .setEmail("test@test.com")
            .build();
        UserProfileDto userProfileDto = new UserProfileDto(user1);

        given(userService.showOne("testId1"))
            .willReturn(userProfileDto);

        // when
        ResultActions resultActions = mvc.perform(get("/users/" + userProfileDto.getUserId()));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(model().attribute("userId", userProfileDto.getUserId()))
            .andExpect(model().attribute("email", userProfileDto.getEmail()))
            .andExpect(view().name("user/profile"));
    }
}
