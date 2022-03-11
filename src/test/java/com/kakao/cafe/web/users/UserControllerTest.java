package com.kakao.cafe.web.users;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.validation.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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

    @MockBean
    private UserValidation userValidation;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Shine", "1234", "Shine", "shine@naver.com");
        user.setId(1L);
        given(userValidation.supports(any())).willReturn(true);
    }

    @Test
    public void saveUserTest() throws Exception {
        // given
        given(userService.register(any())).willReturn(1L);

        // when
        ResultActions requestThenResult = mockMvc.perform(post("/users/create")
                .param("userId", "Shine")
                .param("password", "1234")
                .param("name", "Shine")
                .param("email", "shine@naver.com")
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));
    }

    @Test
    public void findUserByIdTest() throws Exception {
        // given
        given(userService.findUserById(any())).willReturn(user);

        // when
        ResultActions requestThenResult = mockMvc.perform(get("/users/" + user.getUserId())
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("/user/profile"));
    }

    @Test
    public void findAllUsersTest() throws Exception {
        // given
        given(userService.findUsers()).willReturn(Arrays.asList(user));

        // when
        ResultActions requestThenResult = mockMvc.perform(get("/users")
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(model().attribute("users", Arrays.asList(user)))
                .andExpect(view().name("/user/list"));
    }

    @Test
    public void userProfileTest() throws Exception {
        // given
        given(userService.findUserById(any())).willReturn(user);

        // when
        ResultActions requestThenResult = mockMvc.perform(get("/users/" + user.getUserId())
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("/user/profile"));
    }

    @Test
    public void updateUserFormTest() throws Exception {
        // given
        given(userService.findUserById(any())).willReturn(user);

        // when
        ResultActions requestThenResult = mockMvc.perform(get("/users/" + user.getUserId() + "/form")
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("/user/updateForm"));
    }

    @Test
    public void updateSucessTest() throws Exception {
        // given
        User updateUser = new User("Shine", "1234", "ShineUpdate", "update@naver.com");
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("SESSIONED_USER", user);
        given(userService.userUpdate(any())).willReturn(true);

        // when
        ResultActions requestThenResult = mockMvc.perform(post("/users/" + user.getUserId() + "/update")
                .session(mockSession)
                .param("userId", "Shine")
                .param("password", "1234")
                .param("name", "updateShine")
                .param("email", "update@naver.com")
                .accept(MediaType.TEXT_HTML));

        // then
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));
    }

    @Test
    public void updateFailTest() throws Exception {
        // given
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("SESSIONED_USER", user);
        given(userService.userUpdate(any())).willThrow(new NotFoundException("해당 사용자를 찾을 수 없습니다"));

        // then
        assertThatThrownBy(() -> mockMvc.perform(post("/users/" + user.getUserId() + "/update")
                .session(mockSession)
                .param("userId", "Shine")
                .param("password", "1234")
                .param("name", "Shine")
                .param("email", "shine@naver.com")
                .accept(MediaType.TEXT_HTML)
        )).hasCause(new NotFoundException("해당 사용자를 찾을 수 없습니다"));
    }
}
