package com.kakao.cafe;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void getUsersTest() throws Exception {
        // given
        List<User> users = List.of(new User("userId", "password", "name", "email@example.com"));

        given(userRepository.findAll())
            .willReturn(users);

        // when
        ResultActions actions = mockMvc.perform(get("/users")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("users", users))
            .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("유저 아이디로 유저를 조회한다")
    public void getUserTest() throws Exception {
        // given
        User user = new User("userId", "password", "name", "email@example.com");

        given(userRepository.findByUserId("userId"))
            .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(get("/users/userId")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(model().attribute("user", user))
            .andExpect(view().name("user/profile"));
    }

    @Test
    @DisplayName("유저 회원 가입 화면을 보여준다")
    public void getRegisterTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/users/register")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("유저 회원 가입을 진행한다")
    public void postRegisterTest() throws Exception {
        // given
        User user = new User("userId", "password", "name", "email@example.com");

        given(userRepository.save(user))
            .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(post("/users/register")
            .param("userId", user.getUserId())
            .param("password", user.getPassword())
            .param("name", user.getName())
            .param("email", user.getEmail())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(view().name("redirect:/users"));
    }

}

