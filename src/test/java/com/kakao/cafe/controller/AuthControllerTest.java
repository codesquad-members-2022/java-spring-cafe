package com.kakao.cafe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.service.AuthService;
import com.kakao.cafe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@DisplayName("AuthController 단위 테스트")
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("사용자가 로그인 요청을 했을 때 정상 처리 시 /로 리다이렉트한다.")
    @Test
    void 사용자_로그인() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        doNothing().when(authService).validateUser("ikjo", "1234");
        UserResponseDto userResponseDto = user.convertToDto();
        given(userService.findOne("ikjo")).willReturn(userResponseDto);

        MultiValueMap<String, String> userParam = new LinkedMultiValueMap<>();
        userParam.add("userId", "ikjo");
        userParam.add("password", "1234");

        // when
        ResultActions resultActions = mockMvc.perform(post("/user/login").params(userParam).session(session));

        // then
        verify(authService).validateUser("ikjo", "1234");
        assertThat(session.getAttribute("sessionUser")).isEqualTo(userResponseDto);
        resultActions.andExpect(redirectedUrl("/"));
    }

    @DisplayName("사용자가 로그아웃 요청을 했을 때 정상 처리 시 /로 리다이렉트한다.")
    @Test
    void 사용자_로그아웃() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionUser", user.convertToDto());

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/logout").session(session));

        // then
        assertThat(session.isInvalid()).isTrue();
        resultActions.andExpect(redirectedUrl("/"));
    }
}
