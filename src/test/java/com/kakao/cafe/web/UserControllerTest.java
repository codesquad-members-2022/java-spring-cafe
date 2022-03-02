package com.kakao.cafe.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserDto;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("form.html get 테스트")
    void joinForm() throws Exception {
        mockMvc.perform(get("/user/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"));
    }

    @Test
    @DisplayName("/user/create post and 리다이렉션 테스트")
    void joinUser() throws Exception {

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userId=ron2&password=1234&name=ron2&email=ron2@gmail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"))
                .andExpect(redirectedUrl("/users"))
                .andDo(print());

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
