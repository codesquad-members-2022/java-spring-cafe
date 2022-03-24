package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.dto.UserRequestDto;
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
import org.springframework.mock.web.MockHttpSession;

@DisplayName("AuthService 단위 테스트")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setup() {
        userRequestDto = new UserRequestDto("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("사용자가 로그인 요청 시 로그인 정보(아이디)가 올바르지 않은 경우 LoginFailedException 예외가 발생한다.")
    @Test
    void 사용자_로그인_정보_아이디_검증() {
        // given
        given(userRepository.findByUserId("ikjo")).willReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> authService.validateUser("ikjo", "1234"))
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
        assertThatThrownBy(() -> authService.validateUser("ikjo", "4321"))
            .isInstanceOf(LoginFailedException.class)
            .hasMessageContaining("아이디 또는 비밀번호가 틀립니다. 다시 로그인해주세요.");
    }

    @DisplayName("사용자가 사용자 정보를 수정 시 사용자에 대한 세션이 존재하지 않는 경우 UserIncorrectAccessException 예외가 발생한다.")
    @Test
    void 사용자_세션_존재_유무_검증() {
        // when, then
        assertThatThrownBy(() -> authService.validateUserIdOfSession("ikjo", null))
            .isInstanceOf(UserIncorrectAccessException.class)
            .hasMessageContaining("로그인이 정상적으로 되어있지 않습니다.");
    }

    @DisplayName("사용자가 사용자 정보를 수정 시 사용자에 대한 세션 상 ID와 입력받은 사용자 ID 값(파라미터)이 일치하지 않는 경우 UserIncorrectAccessException 예외가 발생한다.")
    @Test
    void 사용자_세션_아이디_검증() {
        // when, then
        assertThatThrownBy(() -> authService.validateUserIdOfSession("honux", userRequestDto.convertToDomain().convertToDto()))
            .isInstanceOf(UserIncorrectAccessException.class)
            .hasMessageContaining("다른 사용자의 게시글을 변경할 수 없습니다. 로그인 상태를 확인해주세요.");
    }
}
