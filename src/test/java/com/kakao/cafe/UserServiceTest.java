package com.kakao.cafe;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {

    UserService service;
    MemoryUserRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new MemoryUserRepository();
        service = new UserService(repository);
    }

    @AfterEach
    void afterEach() {
        repository.clearUserInformationList();
    }

    @DisplayName("사용자가 회원가입을 요청했을 때 사용자 정보가 정상적으로 저장되는가?")
    @Test
    void 회원가입() throws Exception {
        // given
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId("ikjo");
        userInformation.setPassword("1234");
        userInformation.setName("조명익");
        userInformation.setEmail("auddlr100@naver.com");

        // when
        service.join(userInformation);

        // then
        UserInformation result = repository.findUserInformationById(userInformation.getUserId()).get();
        assertThat(result).isEqualTo(userInformation);
    }

    @DisplayName("사용자가 이미 존재하는 ID로 회원가입을 요청했을 때 예외 처리가 되는가?")
    @Test
    void 중복_회원_예외() throws Exception {
        // given
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId("ikjo");
        userInformation.setPassword("1234");
        userInformation.setName("조명익");
        userInformation.setEmail("auddlr100@naver.com");

        // when

        // then
        service.join(userInformation);
        assertThatThrownBy(() -> service.join(userInformation))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 존재하는 사용자입니다.");
    }
}
