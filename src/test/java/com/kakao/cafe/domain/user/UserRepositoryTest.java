package com.kakao.cafe.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        log.info("setUp");
        userRepository = new UserRepository();
    }

    @AfterEach
    void clear() {
        log.info("after");
        userRepository.clear();
    }

    @Test
    @DisplayName("userRepository에서 save테스트")
    void save() {
        //given 준비
        User userA = new User("userID", "password", "rlaxogus", "r@gmail.com");
        User userB = new User("userID2", "password2", "rlaxogus2", "r@gmail.com2");
        //when 실행
        userRepository.save(userA);
        userRepository.save(userB);
        //then 검증
        assertThat(userRepository.findById(userA.getId())).isEqualTo(userA);
        log.info("findById Id = {}, getUserId = {}"
                , userRepository.findById(userA.getId()).getId()
                , userRepository.findById(userA.getId()).getUserId());
        assertThat(userRepository.findById(userB.getId())).isEqualTo(userB);
        log.info("findById Id = {}, getUserId = {}"
                , userRepository.findById(userB.getId()).getId()
                , userRepository.findById(userB.getId()).getUserId());
    }

    @Test
    @DisplayName("userRepository에서 findAll 사이즈 테스트")
    void findAll() {
        //given 준비
        User userA = new User("userID", "password", "rlaxogus", "r@gmail.com");
        User userB = new User("userID2", "password2", "rlaxogus2", "r@gmail.com2");
        //when 실행
        userRepository.save(userA);
        userRepository.save(userB);
        //then 검증
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }
}
