package com.ttasjwi.cafe.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("저장 후 사용자 이름으로 찾기 -> 같은 사용자 찾아짐")
    void saveTest1() {
        // given (준비)
        User saveUser = User.builder()
                .userName("test-user")
                .userEmail("test-user@gmail.com")
                .password("1234")
                .regDate(LocalDate.now())
                .build();

        // when (실행)
        userRepository.save(saveUser);
        User findUser = userRepository.findByUserName(saveUser.getUserName()).get();

        // then (검증)
        assertThat(findUser.getUserName())
                .isEqualTo(saveUser.getUserName());
    }

    @Test
    @DisplayName("저장 후 사용자 이메일로 찾기 -> 같은 사용자 찾아짐")
    void saveTest2() {
        // given (준비)

        User saveUser = User.builder()
                .userName("test-user")
                .userEmail("test-user@gmail.com")
                .password("1234")
                .regDate(LocalDate.now())
                .build();

        // when (실행)
        userRepository.save(saveUser);
        User findUser = userRepository.findByUserEmail(saveUser.getUserEmail()).get();

        // then (검증)
        assertThat(findUser.getUserName())
                .isEqualTo(saveUser.getUserName());
    }

}