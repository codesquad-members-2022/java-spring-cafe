package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.exception.DuplicatedIdException;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UserDto;
import com.kakao.cafe.domain.dto.UserProfileDto;

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
        User user1 = new User("lucid", "1234", "leejo", "leejohy@naver.com");
        User user2 = new User("tesla", "0000", "elon", "elon@naver.com");
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

    @DisplayName("findByUserId를 호출하면 UserProfileDto로 해당 유저의 프로파일을 받는다.")
    @Test
    void findByUserId_test() {
        // given
        User user1 = new User("lucid", "1234", "leejo", "leejohy@naver.com");
        User user2 = new User("tesla", "0000", "elon", "elon@naver.com");
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        UserProfileDto userDto = userRepository.findByUserId("lucid");

        // then
        assertThat(userDto.getEmail()).isEqualTo("leejohy@naver.com");
    }

    @DisplayName("findByUserId로 없는 id를 조회하면 예외가 발생한다.")
    @Test
    void findByUserId_No_User_Test() {
        // given
        User user1 = new User("lucid", "1234", "leejo", "leejohy@naver.com");
        userRepository.save(user1);

        // when & then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userRepository.findByUserId("lee");
        });
    }

    @DisplayName("동일한 아이디로 가입하면 예외가 발생한다.")
    @Test
    void duplicate_id_error() {
        User user1 = new User("lucid", "1234", "leejo", "leejohy@naver.com");
        User user2 = new User("lucid", "0000", "leejo", "leej@naver.com");

        userRepository.save(user1);

        Assertions.assertThrows(DuplicatedIdException.class, () -> {
            userRepository.save(user2);
        });
    }

}