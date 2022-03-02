package com.kakao.cafe.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new MemoryUserRepository();
    }

    @Test
    @DisplayName("user를 저장하면 id를 통해서 찾을 수 있어야 한다.")
    void find_saved_user_by_id() {
        User user = new User("ron2","1234", "로니", "ron2@gmail.com");
        userRepository.save(user);

        Optional<User> targetUser = userRepository.findById("ron2");

        assertThat(targetUser.isPresent()).isTrue();
        assertThat(targetUser.get()).isEqualTo(user);
    }

}
