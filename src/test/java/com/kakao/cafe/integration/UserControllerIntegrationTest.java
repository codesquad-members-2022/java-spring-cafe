package com.kakao.cafe.integration;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @AfterEach
    void close() {
        userService.deleteAll();
    }

    @DisplayName("사용자가 회원가입을 요청하면 사용자 정보를 저장하고 리다이렉트한다.")
    @Test
    void 회원_가입() throws Exception {
        // given
        MultiValueMap<String, String> userParam = new LinkedMultiValueMap<>();
        userParam.add("userId", "ikjo");
        userParam.add("password", "1234");
        userParam.add("name", "조명익");
        userParam.add("email", "auddlr100@naver.com");

        // when
        ResultActions resultActions = mockMvc.perform(post("/users").params(userParam));

        // then : 사용자 정보 저장 유무 확인을 위해 userId 값 검증
        resultActions.andExpect(redirectedUrl("/users"));
    }

    @DisplayName("사용자가 회원 목록을 요청을 했을 때 회원 목록과 함께 model과 view를 반환한다.")
    @Test
    void 회원_목록_보기() throws Exception {
        // given
        User user1 = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
        userService.join(user1);
        User user2 = new User("ikjo93", "1234", "조명익", "auddlr100@naver.com");
        userService.join(user2);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users"));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("/user/list"))
                     .andExpect(model().attribute("users", List.of(user1, user2)));
    }

    @DisplayName("사용자가 특정 회원 프로필을 요청을 했을 때 특정 회원 프로필과 함께 model과 view를 반환한다.")
    @Test
    void 회원_프로필_보기() throws Exception {
        // given
        User user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
        userService.join(user);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/ikjo"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("/user/profile"))
                .andExpect(model().attribute("user", user));
    }

    @DisplayName("사용자가 회원 정보 수정 화면을 요청 했을 때 model과 /user/updateForm view를 반환한다.")
    @Test
    void 회원_정보_수정_화면_보기() throws Exception {
        // given
        User user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
        userService.join(user);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/ikjo/form"));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(view().name("/user/updateForm"))
            .andExpect(model().attribute("user", user));
    }

    @DisplayName("사용자가 사용자 정보 수정을 요청하면 사용자 정보를 정상 처리 시 /users로 리다이렉트한다.")
    @Test
    void 사용자_정보_수정() throws Exception {
        // given
        User user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
        userService.join(user);

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
