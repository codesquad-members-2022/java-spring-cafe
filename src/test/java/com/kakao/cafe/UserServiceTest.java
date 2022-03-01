package com.kakao.cafe;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {

    UserService service;
    MemoryUserRepository repository;
    UserInformation userInformation;

    @BeforeEach
    void beforeEach() {
        repository = new MemoryUserRepository();
        service = new UserService(repository);

        userInformation = new UserInformation();
        userInformation.setUserId("ikjo");
        userInformation.setPassword("1234");
        userInformation.setName("조명익");
        userInformation.setEmail("auddlr100@naver.com");
    }

    @AfterEach
    void afterEach() {
        repository.clearUserInformationList();
    }

    @DisplayName("사용자가 회원가입 요청 시 사용자 정보가 저장된다.")
    @Test
    void 회원_가입() {
        // given, when
        service.join(userInformation);

        // then
        UserInformation result = repository.findUserInformationById(userInformation.getUserId()).get();
        assertThat(result).isEqualTo(userInformation);
    }

    @DisplayName("이미 존재하는 ID로 회원가입 요청 시 예외가 발생한다.")
    @Test
    void 중복_회원_예외() {
        // given, when
        service.join(userInformation);

        // then
        assertThatThrownBy(() -> service.join(userInformation))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 존재하는 사용자입니다.");
    }

    @DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        repository.savaUserInformation(userInformation);

        // when
        UserInformation result = service.findOneUser("ikjo").get();

        // then
        assertThat(result).isEqualTo(userInformation);
    }

    @DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        UserInformation userInformation1 = new UserInformation();
        userInformation1.setUserId("ikjo");
        userInformation1.setPassword("1234");
        userInformation1.setName("조명익");
        userInformation1.setEmail("auddlr100@naver.com");
        repository.savaUserInformation(userInformation);
        repository.savaUserInformation(userInformation1);

        // when
        List<UserInformation> userInformation = service.findAllUsers();

        // then
        assertThat(userInformation.size()).isEqualTo(2);
    }
}
