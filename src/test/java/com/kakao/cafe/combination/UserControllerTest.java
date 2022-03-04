package com.kakao.cafe.combination;

import com.kakao.cafe.dto.UserInformation;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @AfterEach
    void close() {
        userService.deleteAllUsers();
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

        // when
        ResultActions resultActions = mockMvc.perform(post("/users").params(userInformationParam));

        // then : 사용자 정보 저장 유무 확인을 위해 userId 값 검증
        resultActions.andExpect(redirectedUrl("/users?joinedUserId=ikjo"));
    }

    @DisplayName("사용자가 회원 목록을 요청을 했을 때 해당 정보와 함께 model과 view를 반환한다.")
    @Test
    void 회원_목록_보기() throws Exception {
        // given
        UserInformation userInformation1 = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
        userService.join(userInformation1);
        UserInformation userInformation2 = new UserInformation("ikjo93", "1234", "조명익", "auddlr100@naver.com");
        userService.join(userInformation2);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users"));

        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("/user/list"))
                     .andExpect(model().attribute("users", List.of(userInformation1, userInformation2)));
    }

    @DisplayName("사용자가 특정 회원 프로필을 요청을 했을 때 해당 정보와 함께 model과 view를 반환한다.")
    @Test
    void 회원_프로필_보기() throws Exception {
        // given
        UserInformation userInformation = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
        userService.join(userInformation);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/ikjo"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("/user/profile"))
                .andExpect(model().attribute("user", userInformation));
    }
}
