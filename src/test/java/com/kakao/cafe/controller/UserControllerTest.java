package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User createUser(int number) {
        UserForm userForm = new UserForm("userId" + number,
                "userPassword" + number,
                "userName" + number,
                "userEmail" + number + "@naver.com");

        return new User(userForm);
    }

    MultiValueMap createParams(int number) {
        MultiValueMap<String, String> userParams = new LinkedMultiValueMap<>();
        userParams.add("userId", "userId" + number);
        userParams.add("password", "userPassword" + number);
        userParams.add("name", "userName" + number);
        userParams.add("email", "userEmail" + number + "@naver.com");

        return userParams;
    }

    @Test
    @DisplayName("회원가입 페이지로 잘 이동하는지 확인한다.")
    void joinForm() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/user/create"));

        //then
        resultActions.andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("회원가입을 하면, 가입된 회원과 함께 회원 리스트 페이지로 이동해야 한다.")
    void joinUser() throws Exception {
        //given
        MultiValueMap<String, String> userParams = createParams(1);
        User user1 = createUser(1);

        given(userService.join(user1))
                .willReturn(user1.getUserId());

        //when
        ResultActions resultActions = mockMvc.perform(post("/user/create")
                .params(userParams));

        //then
        resultActions.andExpect(redirectedUrl("/list/show"))
                .andExpect(model().attribute("user", user1))
                .andExpect(view().name("redirect:/list/show"));
    }

    @Test
    @DisplayName("회원 리스트 페이지로 이동하면, 회원들의 리스트가 나와야 한다.")
    void allUsers() throws Exception {
        //given
        User user1 = createUser(1);
        List<User> userList = List.of(user1);

        given(userService.findUsers())
                .willReturn(userList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/list/show"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(model().attribute("users", userList))
                .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("유저 프로필 페이지로 들어가면, 해당 유저의 정보가 나와야 한다.")
    void profile() throws Exception {
        //given
        User user1 = createUser(1);

        given(userService.findOne(user1.getUserId()))
                .willReturn(user1);

        //when
        ResultActions resultActions = mockMvc.perform(get("/user/" + user1.getUserId()));

        //then
        resultActions.andExpect(model().attribute("userId", user1.getUserId()))
                .andExpect(model().attribute("email", user1.getEmail()))
                .andExpect(view().name("user/profile"));
    }
}
