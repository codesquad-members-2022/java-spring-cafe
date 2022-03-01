package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UserCreateDto;
import com.kakao.cafe.exception.DuplicateUserIdException;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    private UserCreateDto userCreateDto;

    @BeforeEach
    void setup() {
        userCreateDto = new UserCreateDto("test", "1234", "박우진", "abc@naver.com");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAllUsers();
    }

    @Test
    @DisplayName("회원가입용 데이터가 정상적으로 주어지면 회원가입이 성공한다")
    void joinSuccessTest() {
        // given
        User user = new User(userService.nextUserSequence(), userCreateDto);

        // when
        User resultUser = userService.join(user);

        // then
        assertThat(resultUser.getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    @DisplayName("이미 가입된 userId로 다시 가입을 시도하면 DuplicateUserIdException이 발생한다")
    void joinDuplicateTest() {
        // given
        UserCreateDto userCreateDto2 = new UserCreateDto("test", "1234", "박우진", "abc@naver.com");
        User user = new User(userService.nextUserSequence(), userCreateDto);
        User user2 = new User(userService.nextUserSequence(), userCreateDto2);

        // when
        User resultUser = userService.join(user);
        DuplicateUserIdException e = assertThrows(DuplicateUserIdException.class,
                () -> userService.join(user2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 가입된 ID입니다. 새로운 ID로 가입해주세요.");
    }
}
