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

    @Test
    @DisplayName("같은 아이디의 유저(회원정보가 수정된다면)를 저장하면 , 정보를 덮어쓴다.")
    void save_updateInfo() {
        User user = new User("ron2","1234", "로니", "ron2@gmail.com");
        userRepository.save(user);
        userRepository.findById("ron2").ifPresent(u -> {
                    assertThat(u.getEmail()).isEqualTo("ron2@gmail.com");
                    assertThat(u.getName()).isEqualTo("로니");
                }
        );
        User updateUser = new User("ron2","1234","니로","gmail@ron2.com");
        userRepository.save(updateUser);
        userRepository.findById("ron2").ifPresent(u -> {
                    assertThat(u.getEmail()).isEqualTo("gmail@ron2.com");
                    assertThat(u.getName()).isEqualTo("니로");
                }
        );

    }

}
