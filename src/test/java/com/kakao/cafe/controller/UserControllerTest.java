package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserInformation;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserInformation userInformation;

    @BeforeEach
    void setup() {
        userInformation = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("사용자가 회원가입을 요청하면 사용자 정보를 저장하고 리다이렉트한다.")
    @Test
    void 회원_가입() throws Exception {
        // given
        MultiValueMap<String, String> userInformationParam = new LinkedMultiValueMap<>();
        userInformationParam.add("userId", "ikjo");
        userInformationParam.add("password", "1234");
        userInformationParam.add("name", "조명익");
        userInformationParam.add("email", "auddlr100@naver.com");

        given(userService.join(any(UserInformation.class))).willReturn(userInformation);

        // when
        ResultActions resultActions = mockMvc.perform(post("/users").params(userInformationParam));

        // then : 사용자 정보 저장 유무 확인을 위해 userId 값 검증
        resultActions.andExpect(redirectedUrl("/users?joinedUserId=ikjo"));
    }

    @DisplayName("사용자가 회원 목록을 요청을 했을 때 model과 /user/list view를 반환한다.")
    @Test
    void 회원_목록_보기() throws Exception {
        // given
        given(userService.findAllUsers()).willReturn(List.of(userInformation));

        // when
        ResultActions resultActions = mockMvc.perform(get("/users"));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("/user/list"))
                     .andExpect(model().attribute("users", List.of(userInformation)));
    }

    @DisplayName("사용자가 특정 회원 프로필을 요청을 했을 때 model과 /user/profile view를 반환한다.")
    @Test
    void 회원_프로필_보기() throws Exception {
        // given
        given(userService.findOneUser("ikjo")).willReturn(Optional.of(userInformation));

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/ikjo"));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("/user/profile"))
                     .andExpect(model().attribute("user", userInformation));
    }
}
