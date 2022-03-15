package com.kakao.cafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.controller.dto.UserJoinRequestDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// JUnit 5 부터 @ExtendWith 생략 가능
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    UserJoinRequestDto joinRequestDto;

    @BeforeEach
    void setUp() {
        String userId = "testId";
        String password = "password";
        String name = "suntory";
        String email = "test@test.co.kr";
        joinRequestDto = new UserJoinRequestDto(userId, password, name, email);

    }

    @Test
    @DisplayName("회원가입 화면이 출력된다")
    void joinPage() throws Exception {
        mvc.perform(get("/users/join"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입이 수행된다")
    void joinTest() throws Exception {
        // when
        mvc.perform(post("/users/create")
                        .content(new ObjectMapper().writeValueAsString(joinRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @DisplayName("회원 목록이 조회된다")
    void userListTest() throws Exception {
        // given
        User user = joinRequestDto.toEntity();
        user.setId(0L);

        given(userService.findUsers()).willReturn(List.of(user));

        // when
        mvc.perform(get("/users"))
                // then
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("유저 프로필이 조회된다")
    void userProfileTest() throws Exception {
        // given
        User user = joinRequestDto.toEntity();
        user.setId(0L);

        given(userService.findByUserId(joinRequestDto.getUserId())).willReturn(user);

        String url = "/users/" + joinRequestDto.getUserId();

        // when
        mvc.perform(get(url))
                // then
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"))
                .andExpect(model().attribute("user", user))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원정보 수정 페이지가 출력된다")
    void userUpdatePageTest() throws Exception {
        // given
        User user = joinRequestDto.toEntity();
        given(userService.findByUserId(joinRequestDto.getUserId())).willReturn(user);
        String url = "/users/" + joinRequestDto.getUserId() + "/form";

        // when
        mvc.perform(get(url))
                // then
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("user/updateForm"));
    }

    @Test
    @DisplayName("회원 정보가 수정된다")
    void userUpdateTest() throws Exception {
        // given
        String url = "/users/" + joinRequestDto.getUserId() + "/update";

        String newName = "newSuntory";
        String newEmail = "new@test.co.kr";

        UserUpdateRequestDto updateRequestDto =
                new UserUpdateRequestDto(joinRequestDto.getUserId(), joinRequestDto.getPassword(), joinRequestDto.getPassword(), newName, newEmail);

        //when
        mvc.perform(post(url)
                        .content(new ObjectMapper().writeValueAsString(updateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }
}
