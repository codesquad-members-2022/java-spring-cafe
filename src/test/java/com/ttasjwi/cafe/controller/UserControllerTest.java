package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("\"/users/new\"로 GET 요청하면 \"/users/createUserForm\" 이 반환된다.")
    void createFormTest() throws Exception {
        String requestUrl = "/users/new";

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("/users/createUserForm"));
    }

    @Test
    @DisplayName("\"/users/new\"로 POST 요청하면 \"/users\" 로 리다이렉트 된다.")
    void createTest() throws Exception {
        String requestUrl = "/users/new";

        mockMvc.perform(post(requestUrl)
                        .param("userName","ttasjwi")
                        .param("userEmail","ttasjwi920@gmail.com")
                        .param("password","1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @DisplayName("\"/users\"로 요청하면 \"/users/userList\" 가 반환된다.")
    void listTest() throws Exception {
        String requestUrl = "/users";

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("/users/userList"));
    }
}