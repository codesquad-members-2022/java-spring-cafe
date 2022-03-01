package com.kakao.cafe.web;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("form.html get 테스트")
    void joinForm() throws Exception {
        mockMvc.perform(get("/user/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"));
    }

    @Test
    @DisplayName("list.html get 테스트")
    void showUsersAll() throws Exception {
        User user = new User("ron2", "1234", "ron2", "ron2@gmail.com");
        UserResponseDto userResponseDto = new UserResponseDto(user);

        given(userService.findAll()).willReturn(List.of(userResponseDto));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", List.of(userResponseDto)))
                .andExpect(view().name("list"));
    }

    @Test
    @DisplayName("profile.html get 테스트")
    void showProfile() throws Exception {
        User user = new User("ron2", "1234", "ron2", "ron2@gmail.com");
        UserResponseDto userResponseDto = new UserResponseDto(user);

        given(userService.findUser(any())).willReturn(userResponseDto);

        mockMvc.perform(get("/users/ron2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userResponseDto))
                .andExpect(view().name("profile"));
    }
}
