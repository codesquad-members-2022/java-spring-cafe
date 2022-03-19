package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.web.dto.UserListDto;
import com.kakao.cafe.web.dto.UserLoginFormDto;
import com.kakao.cafe.web.dto.UserProfileDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    UserRegisterFormDto userRegisterFormDto;
    User user1;

    @BeforeEach
    void setup() {
        userRegisterFormDto = new UserRegisterFormDto("testId1", "testPw1", "testName1",
            "test1@test.com");

        user1 = new User.UserBuilder("testId1","testPw1")
            .setName("testName1")
            .setEmail("test1@test.com")
            .build();
    }

    @Test
    @DisplayName("회원가입이 아무 예외 없이 동작한다.")
    void 회원가입_테스트() {
        // given
        doNothing().when(userRepository)
            .save(any());

        given(userRepository.findById(any()))
            .willReturn(Optional.empty());

        // then
        assertThatCode(() -> userService.register(userRegisterFormDto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("동일한 Id를 입력한 회원의 회원가입 시도는 예외가 발생한다.")
    void 중복_ID_회원가입_테스트() {
        // given
        String userId = "testId1";
        given(userRepository.findById(userId))
            .willReturn(Optional.ofNullable(user1));

        // then
        assertThatThrownBy(() -> userService.register(userRegisterFormDto))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("동일한 ID를 가지는 회원이 이미 존재합니다.");
    }

    @Test
    @DisplayName("모든 회원을 조회 할 수 있다.")
    void 모든_회원_조회_테스트() {
        // given
        User user2 = new User.UserBuilder("testId2","testPw2")
            .setName("testName2")
            .setEmail("test2@test.com")
            .build();
        List<User> userList = Arrays.asList(user1, user2);
        given(userRepository.findAll())
            .willReturn(userList);

        // when
        List<UserListDto> resultList = userService.showAll();

        // then
        assertThat(resultList).hasSize(2);
        assertThat(resultList.get(0).getUserId()).isEqualTo("testId1");
        assertThat(resultList.get(1).getUserId()).isEqualTo("testId2");
    }

    @Test
    @DisplayName("ID를 통해 회원을 조회 할 수 있다.")
    void ID를_통한_회원_조회_테스트() {
        // given
        String userId = "testId1";
        given(userRepository.findById("testId1"))
            .willReturn(Optional.ofNullable(user1));

        // when
        UserProfileDto userProfileDto = userService.showOne(userId);

        // then
        assertThat(userProfileDto.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("존재하지 않는 ID의 회원을 조회하려는 시도는 예외가 발생한다.")
    void 존재하지_않는_ID의_회원_조회_테스트() {
        // given
        String userId = "testId3";
        given(userRepository.findById(userId))
            .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> userService.showOne(userId))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("해당 ID를 가지는 회원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("적절한 아이디와 패스워드를 가진 로그인 시도는 아무 예외 없이 동작한다.")
    void 로그인_정상_동작_테스트() {
        // given
        UserLoginFormDto userLoginFormDto = new UserLoginFormDto("testId1", "testPw1");
        given(userRepository.findById("testId1"))
            .willReturn(Optional.ofNullable(user1));

        // when
        String resultId = userService.login(userLoginFormDto);

        // then
        assertThat(resultId).isEqualTo("testId1");
    }

    @Test
    @DisplayName("부적절한 아이디로의 로그인 시도는 예외가 발생한다.")
    void 부적절한_아이디_로그인_예외_발생_테스트() {
        // given
        UserLoginFormDto userLoginFormDto = new UserLoginFormDto("testId3", "testPw3");
        given(userRepository.findById("testId3"))
            .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> userService.login(userLoginFormDto))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("해당 ID를 가지는 회원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("부적절한 비밀번호로의 로그인 시도는 예외가 발생한다.")
    void 부적절한_비밀번호_로그인_예외_발생_테스트() {
        // given
        UserLoginFormDto userLoginFormDto = new UserLoginFormDto("testId1", "testPw3");
        given(userRepository.findById("testId1"))
            .willReturn(Optional.ofNullable(user1));

        // then
        assertThatThrownBy(() -> userService.login(userLoginFormDto))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
