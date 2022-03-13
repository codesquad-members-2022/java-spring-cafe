package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.web.login.dto.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("Shine", "1234", "Shine", "Shine@gmail.com");
        user.setId(1L);
    }

    @Test
    void loginSuccessTest() {
        // given
        given(userRepository.findByUserId("Shine")).willReturn(Optional.ofNullable(user));
        LoginDto loginDto = new LoginDto(user.getUserId(), user.getPassword());

        // when
        User loginUser = loginService.login(loginDto);

        // then
        assertThat(loginUser).isEqualTo(user);
    }

    @Test
    void loginFailedTest() {
        // given
        given(userRepository.findByUserId("NoMember")).willReturn(Optional.empty());
        LoginDto loginDto = new LoginDto("NoMember", "NoPassword");


        // when
        User loginUser = loginService.login(loginDto);

        // then
        assertThat(loginUser).isNull();
    }

    @Test
    void logoutTest() {
        // given
        HttpSession session = new MockHttpSession();
        session.setAttribute("userCookie", "1234");

        // when
        boolean isLogout = loginService.logout(session);

        // then
        assertThat(isLogout).isTrue();
    }
}
