package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import com.kakao.cafe.web.dto.UserJoinDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService userService;
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    void clear() {
        userRepository.clear();
    }

    @Test
    @DisplayName("회원가입")
    void userJoin() {
        //given 준비
        UserJoinDto dto = new UserJoinDto("userID", "password", "rlaxogus", "r@gmail.com");
        //when 실행
        userService.userJoin(dto);
        //then 검증
        User testUser = userRepository.findByUserId(dto.getUserId());
        assertThat(dto.getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    @DisplayName("회원목록 조회")
    void userList() {
        //given 준비
        UserJoinDto dto = new UserJoinDto("userID", "password", "rlaxogus", "r@gmail.com");
        //when 실행
        userService.userJoin(dto);
        //then 검증
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }
}
