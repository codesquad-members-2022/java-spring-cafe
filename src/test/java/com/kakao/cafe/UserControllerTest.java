package com.kakao.cafe;

import com.kakao.cafe.controller.UserController;
import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.service.UserService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @DisplayName("사용자가 회원가입을 요청한 후에 /users로 리다이렉트한다.")
    @Test
    void 회원_가입() throws Exception {
        // given
        MultiValueMap<String, String> userInformation = new LinkedMultiValueMap<>();
        userInformation.add("userId", "ikjo");
        userInformation.add("password", "1234");
        userInformation.add("name", "조명익");
        userInformation.add("email", "auddlr100@naver.com");

        // when
        ResultActions resultActions = mockMvc.perform(post("/users").params(userInformation));

        // then
        resultActions.andExpect(redirectedUrl("/users"));
    }

    @DisplayName("사용자가 회원 목록을 요청을 했을 때 model과 /user/list view를 반환한다.")
    @Test
    void 회원_목록_보기() throws Exception {
        // given
        UserInformation userInformation = new UserInformation();
        userInformation.setId(1L);
        userInformation.setUserId("ikjo");
        userInformation.setPassword("1234");
        userInformation.setName("조명익");
        userInformation.setEmail("auddlr100@naver.com");

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
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId("ikjo");
        userInformation.setPassword("1234");
        userInformation.setName("조명익");
        userInformation.setEmail("auddlr100@naver.com");

        given(userService.findOneUser("ikjo")).willReturn(Optional.of(userInformation));

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/ikjo"));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("/user/profile"))
                     .andExpect(model().attribute("user", userInformation));
    }
}
