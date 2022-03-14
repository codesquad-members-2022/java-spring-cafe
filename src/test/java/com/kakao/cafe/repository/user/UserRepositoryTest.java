package com.kakao.cafe.repository.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.user.User;

class UserRepositoryTest {

    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new MemoryUserRepository();
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
        List<User> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
        for (User user : users) {
            assertThat(user).isInstanceOf(User.class);
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
        Optional<User> user = userRepository.findById("lucid");

        // then
        assertThat(user.get().getEmail()).isEqualTo("leejohy@naver.com");
    }
}
