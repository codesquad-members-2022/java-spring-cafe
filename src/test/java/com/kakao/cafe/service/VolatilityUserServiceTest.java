package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.repository.Repository;
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
    Repository<User, String> repository;

    @AfterEach
    public void afterEach() {
        repository.clear();
    }

    @Test
    void addUserSuccess() {
        //given
        User user = new User(-1, "user", "1234", "name", "user@gmail.com");

        //when
        User newUser = userService.add(user);

        //then
        assertThat(newUser).isEqualTo(user);
    }

    @Test
    void addUserFail() {
        //given
        User user1 = new User(-1, "user", "1234", "name", "user@gmail.com");
        User user2 = new User(-1, "user", "1234", "name", "user@gmail.com");

        //when
        userService.add(user1);

        // then
        assertThatThrownBy(() -> userService.add(user2))
                .isInstanceOf(DuplicateUserIdException.class)
                        .hasMessage(EXISTENT_ID_MESSAGE);
    }
}
