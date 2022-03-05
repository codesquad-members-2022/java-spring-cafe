package com.kakao.cafe.service;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserInformation userInformation;

    @BeforeEach
    void setup() {
        userInformation = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("기존의 없는 ID로 사용자가 회원가입 요청 시 사용자 정보가 저장된다.")
    @Test
    void 회원_가입() {
        // given
        given(userRepository.findByUserId(userInformation.getUserId())).willReturn(Optional.ofNullable(null));
        given(userRepository.save(userInformation)).willReturn(userInformation);

        // when
        UserInformation result = userService.join(userInformation);

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
        given(userRepository.findByUserId(userInformation.getUserId())).willReturn(Optional.ofNullable(userInformation));

        // when, then
        assertThatThrownBy(() -> userService.join(userInformation))
                                            .isInstanceOf(IllegalStateException.class)
                                            .hasMessageContaining("이미 존재하는 사용자입니다.");
    }

    @DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        given(userRepository.findByUserId(userInformation.getUserId())).willReturn(Optional.ofNullable(userInformation));

        // when
        UserInformation result = userService.findOne("ikjo").get();

        // then
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getName()).isEqualTo("조명익");
        assertThat(result.getEmail()).isEqualTo("auddlr100@naver.com");
    }

    @DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        UserInformation otherUserInformation = new UserInformation("ikjo93", "1234", "조명익", "auddlr100@naver.com");
        given(userRepository.findAll()).willReturn(List.of(userInformation, otherUserInformation));

        // when
        List<UserInformation> result = userService.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("기존 사용자 정보를 수정한다.")
    @Test
    void 사용자_정보_수정() {
        // given
        UserInformation updatedUserInformation = new UserInformation("ikjo", "1234", "익조", "auddlr100@naver.com");
        given(userRepository.findByUserId("ikjo")).willReturn(Optional.of(userInformation));
        given(userRepository.save(updatedUserInformation)).willReturn(updatedUserInformation);

        // when
        UserInformation result = userService.update("ikjo", updatedUserInformation);

        // then
        assertThat(result.getName()).isEqualTo("익조");
    }
}
