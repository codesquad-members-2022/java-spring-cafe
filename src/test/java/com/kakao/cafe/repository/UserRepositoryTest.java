package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    void save() {
        User user = new User("saveUserId", "password", "name", "save@gmail.com");

        User saveUser = userRepository.save(user);

        assertThat(saveUser).isEqualTo(user);
    }

    @Test
    void findByUserId() {
        User user = new User("findUserId", "password", "name", "find@gmail.com");

        userRepository.save(user);
        User findUser = userRepository.findByUserId(user.getUserId());

        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("사용자를 찾지 못했다면 예외가 발생한다.")
    void ifUserNotFoundThrowException() {
        assertThatThrownBy(() -> userRepository.findByUserId("isNotFindUserId"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자가 존재하지 않습니다.");
    }

    @Test
    void isExistUserId() {
        User user = new User("existUserId", "password", "name", null);
        userRepository.save(user);

        boolean result1 = userRepository.isExistUserId("existUserId");
        boolean result2 = userRepository.isExistUserId("notExistUserId");

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void isExistEmail() {
        User user = new User("existEmailUserId", "password", "name", "exist@gmail.com");
        userRepository.save(user);

        boolean result1 = userRepository.isExistEmail("exist@gmail.com");
        boolean result2 = userRepository.isExistEmail("notExistUserId");

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }
}
