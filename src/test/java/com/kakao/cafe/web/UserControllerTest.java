package com.kakao.cafe.web;

import com.kakao.cafe.constants.LoginConstants;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.SessionUser;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private MockHttpSession httpSession = new MockHttpSession();
    private UserResponseDto userResponseDto;
    private SessionUser sessionUser;

    @BeforeEach
    void setUp() {
        User user = new User("ron2", "1234", "ron2", "ron2@gmail.com");
        userResponseDto = new UserResponseDto(user);
        sessionUser = new SessionUser(user.getUserId(), user.getName(), user.getEmail());
    }

    @AfterEach
    public void clear(){
        httpSession.clearAttributes();
    }

    @Test
    @DisplayName("GetMapping 회원가입버튼을 누르면 회원가입폼으로 이동한다.")
    void joinForm() throws Exception {
        mockMvc.perform(get("/user/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("PostMapping 회원가입폼 작성 후 버튼을 누르고 회원가입이 성공하면 유저 목록으로 리다이렉션된다.")
    void joinUser() throws Exception {

        mockMvc.perform(post("/user/sign-up")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userId=ron2&password=1234&name=ron2&email=ron2@gmail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

    }

    @Test
    @DisplayName("GetMapping 유저목록을 model로 받아서 뷰에서 보여준다.")
    void showUsersAll() throws Exception {

        given(userService.findAll()).willReturn(List.of(userResponseDto));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", List.of(userResponseDto)))
                .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("GetMapping UserResponseDTO를 모델로 받아서 뷰에서 보여준다.")
    void showProfile() throws Exception {
        httpSession.setAttribute("sessionedUser",userResponseDto);
        given(userService.findUser(any())).willReturn(userResponseDto);

        mockMvc.perform(get("/users/ron2").session(httpSession))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userResponseDto))
                .andExpect(view().name("user/profile"));
    }

    @Test
    @DisplayName("GetMapping userId를 @PathVariable로 받아서 해당 유저 정보를 /user/update_form으로 넘겨준다.")
    void updateFormTest() throws Exception {

        httpSession.setAttribute("sessionedUser", sessionUser);

        mockMvc.perform(get("/users/ron2/update").session(httpSession))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", sessionUser))
                .andExpect(view().name("user/update_form"))
                .andDo(print());
    }

    @Test
    @DisplayName("updatform get 요청시, 로그인이 되어있지 않으면(session이 없으면) user/login으로 리다이렉션 된다.")
    void updateForm_notLoggedIn_Test() throws Exception {
        //when
        mockMvc.perform(get("/users/ron2/update").session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"))
                .andDo(print());
    }

    @Test
    @DisplayName("updatform get 요청시, session과 id가 일치하지않으면 error-page/4xx을 반환한다.")
    void updateForm_another_user_access_Test() throws Exception {
        //given
        httpSession.setAttribute("sessionedUser", sessionUser);

        //when
        mockMvc.perform(get("/users/anotherId/update").session(httpSession))
                //then
                .andExpect(status().isForbidden())
                .andExpect(view().name("error-page/4xx"))
                .andExpect(model().attribute("message","접근 권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("PutMapping 수정정보를 받아서 회원정보를 수정 후 /users로 리다이렉션한다.")
    void updateInfoTest() throws Exception {
        //given
        httpSession.setAttribute("sessionedUser", sessionUser);

        //when
        mockMvc.perform(put("/users/ron2/update")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("userId=ron2&password=1234&name=ron2&email=ron2@gmail.com").session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @DisplayName("updateInfo put 요청시 로그인이 되어 있지 않으면(세션이 없으면) /user/login으로 리다이렉션 된다.")
    void updateInfo_notLoggedIn_User_test() throws Exception {

        //when
        mockMvc.perform(put("/users/ron2/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userId=ron2&password=1234&name=ron2&email=ron2@gmail.com").session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    @DisplayName("updateInfo put 요청시, 로그인된 유저가 아닌 다른 유저의 정보를 변경하려고 하면 ClientException이 발생한다. ")
    void updateInfo_another_user_access_Test() throws Exception {
        //given
        httpSession.setAttribute("sessionedUser", sessionUser);

        //when
        mockMvc.perform(put("/users/anotherId/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userId=ron2&password=1234&name=ron2&email=ron2@gmail.com").session(httpSession))
                //then
                .andExpect(status().isForbidden())
                .andExpect(view().name("error-page/4xx"))
                .andExpect(model().attribute("message", "접근 권한이 없습니다."));
    }

    @Test
    @DisplayName("/user/login get 요청시 user/login을 반환한다.")
    void loginFormGetTest() throws Exception {
        //when
        mockMvc.perform(get("/user/login"))
                //then
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
    }

    @Test
    @DisplayName("/user/login post 요청시 user/login을 반환한다.")
    void loginPostTest() throws Exception {
        //given
        given(userService.login(any())).willReturn(Optional.ofNullable(sessionUser));

        //when
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userId=ron2&password=1234"))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/all"))
                .andExpect(request().sessionAttribute("sessionedUser", sessionUser));
    }

    @Test
    @DisplayName("/user/login post 요청시, 회원가입되어있지않은 아이디 혹은 잘못된 비밀번호를 입력했을때 user/login_failed를 반환한다.")
    void loginPostTest_wrongId_and_wrongPassword_test() throws Exception {
        //given
        given(userService.login(any())).willReturn(Optional.empty());

        //when
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userId=wrongId&password=wrongPassword"))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"))
                .andExpect(flash().attribute(LoginConstants.LOGIN_FAILED,"아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요."));
    }

    @Test
    @DisplayName("/user/logout get 요청시 세션이 무효화되고, /qna/all을 리다이렉션한다.")
    void logoutTest() throws Exception {

        //when
        mockMvc.perform(get("/user/logout")
                        .session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/all"));

        verify(userService, only()).logout(httpSession);
    }
}
