package com.kakao.cafe.repository;

import com.kakao.cafe.domain.UserInformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    private UserRepository userRepository = new MemoryUserRepository();

    private UserInformation userInformation;

    @BeforeEach
    void setup() {
        userInformation = new UserInformation("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @AfterEach
    void close() {
        userRepository.clear();
    }

    @DisplayName("주어진 UserInformation 객체의 사용자 정보 데이터를 저장한다.")
    @Test
    void 사용자_정보_저장() {
        // when
        userRepository.save(userInformation);

        // then
        UserInformation result = userRepository.findByUserId(userInformation.getUserId()).get();
        assertThat(result).isEqualTo(userInformation);
    }

    @DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        userRepository.save(userInformation);

        // when
        UserInformation result = userRepository.findByUserId(userInformation.getUserId()).get();

        // then
        assertThat(result).isEqualTo(userInformation);
    }

    @DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        userRepository.save(userInformation);
        userRepository.save(new UserInformation("ikjo93", "1234", "조명익", "auddlr100@naver.com"));

        // when
        List<UserInformation> allUserInformation = userRepository.findAll();

        // then
        assertThat(allUserInformation.size()).isEqualTo(2);
    }

    @DisplayName("기존 사용자 정보를 수정한다.")
    @Test
    void 사용자_정보_수정() {
        // given
        userRepository.save(userInformation);

        // when
        userRepository.save(new UserInformation("ikjo", "1234", "익조", "auddlr100@naver.com"));

        // then
        UserInformation result = userRepository.findByUserId(userInformation.getUserId()).get();
        List<UserInformation> allUserInformation = userRepository.findAll();

        assertThat(result.getName()).isEqualTo("익조");
        assertThat(allUserInformation.size()).isEqualTo(1);
    }
}
