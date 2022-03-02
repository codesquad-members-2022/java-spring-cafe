package com.kakao.cafe.service;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private MemoryUserRepository userRepository;

    private UserInformation userInformation;

    @BeforeEach
    void setup() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
        userInformation = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @AfterEach
    void close() {
        userRepository.clearUserInformationList();
    }

    @DisplayName("사용자가 회원가입 요청 시 사용자 정보가 저장된다.")
    @Test
    void 회원_가입() {
        // when
        userService.join(userInformation);

        // then
        assertThat(userRepository.getCountOfUserInformationElement()).isEqualTo(1);
    }

    @DisplayName("이미 존재하는 ID로 회원가입 요청 시 예외가 발생한다.")
    @Test
    void 중복_회원_예외() {
        // given
        userRepository.savaUserInformation(userInformation);

        // when, then
        assertThatThrownBy(() -> userService.join(userInformation))
                                        .isInstanceOf(IllegalStateException.class)
                                        .hasMessageContaining("이미 존재하는 사용자입니다.");
    }

    @DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        userRepository.savaUserInformation(userInformation);

        // when
        UserInformation result = userService.findOneUser("ikjo").get();

        // then
        assertThat(result).isEqualTo(userInformation);
    }

    @DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        userRepository.savaUserInformation(userInformation);
        UserInformation userInformation1 = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
        userRepository.savaUserInformation(userInformation1);

        // when
        List<UserInformation> userInformation = userService.findAllUsers();

        // then
        assertThat(userInformation.size()).isEqualTo(2);
    }
}
