package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.repository.VolatilityUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kakao.cafe.message.UserMessage.EXISTENT_ID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
class VolatilityUserServiceTest {

    @Autowired
    VolatilityUserService userService;

    @Autowired
    VolatilityUserRepository userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.clear();
    }

    @Test
    void addUserSuccess() {
        //given
        User user = User.builder("user").build();

        //when
        User newUser = userService.addUser(user);

        //then
        assertThat(newUser).isEqualTo(user);
    }

    @Test
    void addUserFail() {
        //given
        User user1 = User.builder("user").build();
        User user2 = User.builder("user").build();

        //when
        userService.addUser(user1);
        assertThatThrownBy(() -> userService.addUser(user2))
                .isInstanceOf(DuplicateUserIdException.class)
                        .hasMessage(EXISTENT_ID_MESSAGE);
    }
}
