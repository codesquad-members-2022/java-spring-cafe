package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    private final UserRepository userRepository = new MemoryUserRepository();

    private User user;

    @BeforeEach
    void setup() {
        user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @AfterEach
    void close() {
        userRepository.clear();
    }

    @DisplayName("주어진 user 객체의 사용자 정보 데이터를 저장한다.")
    @Test
    void 사용자_정보_저장() {
        // when
        userRepository.save(user);

        // then
        User result = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));
        assertThat(result).isEqualTo(user);
    }

    @DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        userRepository.save(user);

        // when
        User result = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));

        // then
        assertThat(result).isEqualTo(user);
    }

    @DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        userRepository.save(user);
        userRepository.save(new User("ikjo93", "1234", "조명익", "auddlr100@naver.com"));

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }

    @DisplayName("기존 사용자 정보를 수정한다.")
    @Test
    void 사용자_정보_수정() {
        // given
        userRepository.save(user);
        userRepository.save(new User("ikjo", "1234", "익조", "auddlr100@naver.com"));

        // when
        User result = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));

        // then
        assertThat(result.getName()).isEqualTo("익조");
    }
}
