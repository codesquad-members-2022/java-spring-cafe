package com.kakao.cafe.web.login;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.LoginService;
import org.junit.jupiter.api.AfterEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    User user;
    MockHttpSession mySession;

    @BeforeEach
    public void setUp() {
        user = new User("Shine", "1234", "Shine", "Shine@gmail.com");
        user.setId(1L);

        mySession = new MockHttpSession();
    }

    @Test
    @DisplayName("로그인 폼 요청")
    void getLoginFormTest() throws Exception {
        // when
        ResultActions requestThenResult = mockMvc.perform(get("/login").accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(view().name("/user/login"));
    }

    @Test
    @DisplayName("정상 로그인 테스트")
    void loginSuccessTest() throws Exception {
        // given
        given(loginService.login(any(), any())).willReturn(user);

        // when
        ResultActions requestThenResult = mockMvc.perform(post("/login")
                .param("userId", "Shine")
                .param("password", "1234")
                .accept(MediaType.TEXT_HTML_VALUE)
                .session(mySession)
        );

        // then
        then(loginService).should(only()).login(any(), any());
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttribute("SESSIONED_USER", user));

        // 세션 반환 부분 꼭 질문하기! 첫 요청으로부터 세션이 정상적으로 생성됨을 어떻게 확인해야 하는가??
    }

    @Test
    @DisplayName("비정상 로그인 테스트")
    void loginFailedTest() throws Exception {
        // given
        given(loginService.login(any(), any())).willReturn(null);

        // when
        ResultActions requestThenResult = mockMvc.perform(post("/login")
                .param("userId", "Shine")
                .param("password", "1234")
                .accept(MediaType.TEXT_HTML_VALUE)
                .session(mySession)
        );

        // then
        then(loginService).should(only()).login(any(), any());
        requestThenResult.andExpect(status().is4xxClientError())
                .andExpect(view().name("/user/login"))
                .andExpect(request().sessionAttributeDoesNotExist("SESSIONED_USER"));
    }

    @Test
    @DisplayName("정상 로그아웃 테스트")
    void logoutSuccessTest() throws Exception {
        // given
        mySession.setAttribute("SESSIONED_USER", user);
        given(loginService.logout(mySession)).willReturn(true);

        // when
        ResultActions requestThenResult = mockMvc.perform(post("/logout")
                .session(mySession)
                .accept(MediaType.TEXT_HTML_VALUE)
        );

        // then
        then(loginService).should(only()).logout(any()); // logout 메서드가 호출 됬어야 한다.
        // logout이 호출되지 않았다면 위에서 검증이 중단됨, 따라서 세션이 삭제는 위 메서드가 호출됬을때만 실행
        mySession.removeAttribute("SESSIONED_USER");
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(request().sessionAttributeDoesNotExist("SESSIONED_USER"));
    }

    @AfterEach
    void tearDown() {
        mySession.clearAttributes();
    }
}
