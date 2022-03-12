package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.repository.Repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kakao.cafe.message.UserMessage.EXISTENT_ID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    Repository<User, String> repository;

    @Test
    @DisplayName("사용자 목록에 존재하지 않는 ID의 가입 요청이 오면 사용자 등록에 성공한다.")
    void addUserSuccess() {
        //given
        NewUserParam newUserParam = new NewUserParam("user", "1234","name", "user@gmail.com");
        User user = newUserParam.convertToUser();

        //when
        User newUser = userService.add(newUserParam);

        //then
        assertThat(newUser).isEqualTo(user);
    }

    @Test
    @DisplayName("사용자 목록에 존재하는 ID의 가입 요청이 오면 사용자 등록에 실패한다.")
    void addUserFail() {
        //given
        NewUserParam newUserParam1 = new NewUserParam("user", "1234","name", "user@gmail.com");
        NewUserParam newUserParam2 = new NewUserParam("user", "1234","name", "user@gmail.com");

        //when
        userService.add(newUserParam1);

        // then
        assertThatThrownBy(() -> userService.add(newUserParam2))
                .isInstanceOf(DuplicateUserIdException.class)
                        .hasMessage(EXISTENT_ID_MESSAGE);
    }
}
