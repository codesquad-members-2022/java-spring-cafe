package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.exception.LoginFailedException;
import com.kakao.cafe.exception.UserIncorrectAccessException;
import com.kakao.cafe.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("UserService 단위 테스트")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setup() {
        userRequestDto = new UserRequestDto("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("기존의 없는 ID로 사용자가 회원가입 요청 시 사용자 정보가 저장된다.")
    @Test
    void 회원_가입() {
        // given
        given(userRepository.findByUserId("ikjo")).willReturn(Optional.ofNullable(null));
        given(userRepository.save(any(User.class))).willReturn(
            new User("ikjo", "1234", "조명익", "auddlr100@naver.com"));

        // when
        User result = userService.join(userRequestDto);

        // then
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getName()).isEqualTo("조명익");
        assertThat(result.getEmail()).isEqualTo("auddlr100@naver.com");
    }

    @DisplayName("이미 존재하는 ID로 회원가입 요청 시 예외가 발생한다.")
    @Test
    void 중복_사용자_예외() {
        // given
        given(userRepository.findByUserId("ikjo")).willReturn(
            Optional.ofNullable(new User("ikjo", "1234", "조명익", "auddlr100@naver.com")));

        // when, then
        assertThatThrownBy(() -> userService.join(userRequestDto))
            .isInstanceOf(UserIncorrectAccessException.class)
            .hasMessageContaining("이미 존재하는 사용자입니다.");
    }

    @DisplayName("기존 사용자 정보를 수정한다.")
    @Test
    void 사용자_정보_수정() {
        // given
        given(userRepository.findByUserId("ikjo")).willReturn(
            Optional.ofNullable(new User("ikjo", "1234", "조명익", "auddlr100@naver.com")));
        given(userRepository.save(any(User.class))).willReturn(
            new User("ikjo", "1234", "익조", "auddlr100@naver.com"));

        // when
        User result = userService.update("ikjo", userRequestDto);

        // then
        assertThat(result.getName()).isEqualTo("익조");
    }

    @DisplayName("사용자가 로그인 요청 시 로그인 정보(아이디)가 올바르지 않은 경우 LoginFailedException 예외가 발생한다.")
    @Test
    void 사용자_로그인_정보_아이디_검증() {
        // given
        given(userRepository.findByUserId("ikjo")).willReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> userService.validateUser("ikjo", "1234"))
            .isInstanceOf(LoginFailedException.class)
            .hasMessageContaining("아이디 또는 비밀번호가 틀립니다. 다시 로그인해주세요.");
    }

    @DisplayName("사용자가 로그인 요청 시 로그인 정보(비밀번호)가 올바르지 않은 경우 LoginFailedException 예외가 발생한다.")
    @Test
    void 사용자_로그인_정보_비밀번호_검증() {
        // given
        given(userRepository.findByUserId("ikjo")).willReturn(
            Optional.ofNullable(userRequestDto.convertToDomain()));

        // when, then
        assertThatThrownBy(() -> userService.validateUser("ikjo", "4321"))
            .isInstanceOf(LoginFailedException.class)
            .hasMessageContaining("아이디 또는 비밀번호가 틀립니다. 다시 로그인해주세요.");
    }

    @DisplayName("사용자가 사용자 정보를 수정 시 사용자에 대한 세션이 존재하지 않는 경우 UserIncorrectAccessException 예외가 발생한다.")
    @Test
    void 사용자_세션_존재_유무_검증() {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionUser", null);

        // when, then
        assertThatThrownBy(() -> userService.validateSessionOfUser("ikjo", session))
            .isInstanceOf(UserIncorrectAccessException.class)
            .hasMessageContaining("로그인이 정상적으로 되어있지 않습니다.");
    }

    @DisplayName("사용자가 사용자 정보를 수정 시 사용자에 대한 세션 상 ID와 입력받은 사용자 ID 값(파라미터)이 일치하지 않는 경우 UserIncorrectAccessException 예외가 발생한다.")
    @Test
    void 사용자_세션_아이디_검증() {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionUser", userRequestDto.convertToDomain().convertToDto());

        // when, then
        assertThatThrownBy(() -> userService.validateSessionOfUser("honux", session))
            .isInstanceOf(UserIncorrectAccessException.class)
            .hasMessageContaining("해당 계정의 정보는 수정할 수 없습니다.");
    }

    @DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        given(userRepository.findAll()).willReturn(
            List.of(new User("ikjo", "1234", "조명익", "auddlr100@naver.com"),
                new User("ikjo93", "1234", "조명익", "auddlr100@naver.com")));

        // when
        List<UserResponseDto> result = userService.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        given(userRepository.findByUserId("ikjo")).willReturn(
            Optional.ofNullable(new User("ikjo", "1234", "조명익", "auddlr100@naver.com")));

        // when
        UserResponseDto result = userService.findOne("ikjo");

        // then
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getName()).isEqualTo("조명익");
        assertThat(result.getEmail()).isEqualTo("auddlr100@naver.com");
    }
}
