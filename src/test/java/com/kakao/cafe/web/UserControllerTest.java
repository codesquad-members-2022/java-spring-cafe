package com.kakao.cafe.web;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
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

    @Test
    @DisplayName("GetMapping 회원가입버튼을 누르면 회원가입폼으로 이동한다.")
    void joinForm() throws Exception {
        mockMvc.perform(get("/user/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/form"));
    }

    @Test
    @DisplayName("PostMapping 회원가입폼 작성 후 버튼을 누르고 회원가입이 성공하면 유저 목록으로 리다이렉션된다.")
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
    @DisplayName("GetMapping 유저목록을 model로 받아서 뷰에서 보여준다.")
    void showUsersAll() throws Exception {
        User user = new User("ron2", "1234", "ron2", "ron2@gmail.com");
        UserResponseDto userResponseDto = new UserResponseDto(user);

        given(userService.findAll()).willReturn(List.of(userResponseDto));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", List.of(userResponseDto)))
                .andExpect(view().name("/user/list"));
    }

    @Test
    @DisplayName("GetMapping UserResponseDTO를 모델로 받아서 뷰에서 보여준다.")
    void showProfile() throws Exception {
        User user = new User("ron2", "1234", "ron2", "ron2@gmail.com");
        UserResponseDto userResponseDto = new UserResponseDto(user);

        given(userService.findUser(any())).willReturn(userResponseDto);

        mockMvc.perform(get("/users/ron2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userResponseDto))
                .andExpect(view().name("/user/profile"));
    }
}
