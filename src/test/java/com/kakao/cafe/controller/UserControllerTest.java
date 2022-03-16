package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("UserController 단위 테스트")
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("사용자가 회원가입을 요청하면 사용자 정보를 저장하고 /users로 리다이렉트한다.")
    @Test
    void 회원_가입() throws Exception {
        // given
        MultiValueMap<String, String> userParams = new LinkedMultiValueMap<>();
        userParams.add("userId", "ikjo");
        userParams.add("password", "1234");
        userParams.add("name", "조명익");
        userParams.add("email", "auddlr100@naver.com");

        given(userService.join(any(UserRequestDto.class))).willReturn(user);

        // when
        ResultActions resultActions = mockMvc.perform(post("/users").params(userParams));

        // then
        resultActions.andExpect(redirectedUrl("/users"));
    }

    @DisplayName("사용자가 회원 목록을 요청을 했을 때 model과 /user/list view를 반환한다.")
    @Test
    void 회원_목록_보기() throws Exception {
        // given
        UserResponseDto userResponseDto = user.convertToDto();
        given(userService.findAll()).willReturn(List.of(userResponseDto));

        // when
        ResultActions resultActions = mockMvc.perform(get("/users"));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("/user/list"))
                     .andExpect(model().attribute("users", List.of(userResponseDto)));
    }

    @DisplayName("사용자가 특정 회원 프로필을 요청을 했을 때 model과 /user/profile view를 반환한다.")
    @Test
    void 회원_프로필_보기() throws Exception {
        // given
        UserResponseDto userResponseDto = user.convertToDto();
        given(userService.findOne("ikjo")).willReturn(userResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/ikjo"));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("/user/profile"))
                     .andExpect(model().attribute("user", userResponseDto));
    }

    @DisplayName("사용자가 회원 정보 수정 화면을 요청 했을 때 model과 /user/updateForm view를 반환한다.")
    @Test
    void 회원_정보_수정_화면_보기() throws Exception {
        // given
        UserResponseDto userResponseDto = user.convertToDto();
        given(userService.findOne("ikjo")).willReturn(userResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/ikjo/form"));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(view().name("/user/updateForm"))
            .andExpect(model().attribute("user", userResponseDto));
    }

    @DisplayName("사용자가 사용자 정보 수정을 요청하면 사용자 정보를 정상 처리 시 /users로 리다이렉트한다.")
    @Test
    void 사용자_정보_수정() throws Exception {
        // given
        given(userService.update(eq("ikjo"), any(UserRequestDto.class))).willReturn(user);

        MultiValueMap<String, String> userParam = new LinkedMultiValueMap<>();
        userParam.add("userId", "ikjo");
        userParam.add("password", "1234");
        userParam.add("name", "ikjo93");
        userParam.add("email", "auddlr100@naver.com");

        // when
        ResultActions resultActions = mockMvc.perform(put("/users/ikjo/update").params(userParam));

        // then
        resultActions.andExpect(redirectedUrl("/users"));
    }
}
