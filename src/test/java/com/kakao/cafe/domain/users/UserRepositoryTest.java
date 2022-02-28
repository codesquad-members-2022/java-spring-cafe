package com.kakao.cafe.domain.users;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepository(new ArrayList<>());
    }

    @DisplayName("findAll을 호출하면 UserDto로 User 목록을 반환받는다.")
    @Test
    void findAll_Test() {
        // given
        User user1 = User.builder()
            .userId("lucid")
            .password("1234")
            .email("lucid@gmail.com")
            .name("leejo")
            .build();

        User user2 = User.builder()
            .userId("tesla")
            .password("1111")
            .email("tesla@gmail.com")
            .name("elon")
            .build();

        userRepository.save(user1);
        userRepository.save(user2);

        // when
        List<UserDto> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
        for (UserDto user : users) {
            assertThat(user).isInstanceOf(UserDto.class);
        }
    }

}