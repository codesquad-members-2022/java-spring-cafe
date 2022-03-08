package com.kakao.cafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    User createUser(int number) {
        UserForm userForm = new UserForm("userId" + number,
                "userPassword" + number,
                "userName" + number,
                "userEmail" + number + "@naver.com");

        return new User(userForm);
    }

    @Test
    @DisplayName("회원가입 페이지로 잘 이동하는지 확인한다.")
    void form() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/user/create"));

        //then
        resultActions.andExpect(view().name("user/form"));
    }

    @Test
        //아직 어떻게 할지 잘 모르겠어서 일단 넘어갔습니다.
    void create() throws Exception {

    }

    @Test
    @DisplayName("회원 리스트 페이지로 이동하면, 회원들의 리스트가 나와야 한다.")
    void list() throws Exception {
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
