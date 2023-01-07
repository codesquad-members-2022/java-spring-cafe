package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.LoginDto;
import com.kakao.cafe.web.dto.SessionUser;
import com.kakao.cafe.web.dto.UserDto;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("ron2", "1234", "로니", "ron2@gmail.com");
    }

    @Test
    @DisplayName("user를 저장할 수 있다.")
    void saveTest() {

        given(userRepository.save(any())).willReturn(user);
        UserDto userDto = new UserDto("ron2", "1234", "로니", "ron2@gmail.com");

        User joined = userService.join(userDto);

        assertThat(joined).isEqualTo(userDto.toEntity());

    }

    @Test
    @DisplayName("중복된 Id를 가진 user를 회원가입할 시 예외가 발생한다.")
    void join_duplicateId() {

        UserDto duplicatedUserDto = new UserDto("ron2", "1111", "니로", "2ron@naver.com");
        given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));

        assertThatThrownBy(() -> userService.join(duplicatedUserDto))
                .isInstanceOf(ClientException.class)
                .hasMessage("아이디가 이미 존재합니다.");
    }

    @Test
    @DisplayName("findAll()메서드를 호출하면 List<UserResponseDto>를 반환한다.")
    void findAllTest() {
        UserDto userDto1 = new UserDto("ron2", "1234", "로니", "ron2@gmail.com");
        UserDto userDto2 = new UserDto("ron3", "1111", "니로", "3ron@naver.com");
        given(userRepository.findAll()).willReturn(List.of(userDto1.toEntity(), userDto2.toEntity()));

        List<UserResponseDto> users = userService.findAll();

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getUserId()).isEqualTo(userDto1.getUserId());
        assertThat(users.get(0).getUserId()).isEqualTo(userDto1.getUserId());
    }

    @Test
    @DisplayName("findUser(userId)메서드를 호출하면 UserResponseDto를 반환한다.")
    void findUserTest() {

        given(userRepository.findById(any())).willReturn(Optional.of(user));

        UserResponseDto dto = userService.findUser("ron2");

        assertThat(dto.getUserId()).isEqualTo(user.getUserId());
        assertThat(dto.getName()).isEqualTo(user.getName());
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("회원가입되지않은 아이디를 찾으면 ClientException이 발생한다.")
    void findUserThrowTest() {

        given(userRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUser("notJoinedId"))
                .isInstanceOf(ClientException.class)
                .hasMessage("찾으시는 유저가 없습니다.");

    }

    @Test
    @DisplayName("같은 아이디와 같은 비밀번호를 가졌다면 유저정보를 수정한다.")
    void updateUserInfoTest() {
        UserDto updateUserDto = new UserDto("ron2", "1234", "니로", "2ron@naver.com");
        given(userRepository.save(any())).willReturn(user);
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        User updatedUser = userService.updateUserInfo(updateUserDto);

        assertThat(updatedUser).isEqualTo(user);
        assertThat(updatedUser.getName()).isEqualTo(updateUserDto.getName());
        assertThat(updatedUser.getEmail()).isEqualTo(updateUserDto.getEmail());
    }

    @Test
    @DisplayName("유저정보를 업데이트할 때, 비밀번호가 다르다면 ClientExceptin이 발생한다.")
    void updateUserInfo_wrongPassword_throw_test() {

        UserDto updateUserDto = new UserDto("ron2", "1111", "니로", "2ron@naver.com");
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.updateUserInfo(updateUserDto))
                .isInstanceOf(ClientException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("회원가입이 되어있는 유저라면 로그인을 할 수 있다.")
    void loginTest() {
        //given
        LoginDto loginUser = new LoginDto("ron2", "1234");
        given(userRepository.findById("ron2")).willReturn(Optional.of(user));

        //when
        SessionUser login = userService.login(loginUser).orElse(null);

        //then
        assertThat(login.getUserId()).isEqualTo(loginUser.getUserId());
        assertThat(login.getUserId()).isEqualTo(user.getUserId());
        assertThat(login.getName()).isEqualTo(user.getName());
        assertThat(login.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("회원가입이 되어있지않은 유저로 로그인을 시도하면 null을 반환한다.")
    void login_not_signup_user_throw_test() {
        //given
        LoginDto loginUser = new LoginDto("notRegistered", "1234");
        given(userRepository.findById(any())).willReturn(Optional.empty());

        //when
        Optional<SessionUser> loginUserOptional = userService.login(loginUser);

        //then
        assertThat(loginUserOptional).isEmpty();
    }

    @Test
    @DisplayName("로그인시 패스워드가 일치하지 않는다면 null을 반환한다.")
    void login_wrong_password_throw_test() {
        //given
        LoginDto loginUser = new LoginDto("ron2", "wrongPassword");
        given(userRepository.findById("ron2")).willReturn(Optional.of(user));

        //when
        Optional<SessionUser> loginUserOptional = userService.login(loginUser);

        //then
        assertThat(loginUserOptional).isEmpty();

    }

    @Test
    @DisplayName("로그아웃시 session을 삭제한다")
    void logoutTest() {
        //given
        HttpSession httpSession = new MockHttpSession();

        //when
        userService.logout(httpSession);

        //then session이 무효화되었는지 확인 (service에서 session을 무효화할 필요가 있을까..?)
        assertThatThrownBy(httpSession::invalidate)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The session has already been invalidated");

    }

}
