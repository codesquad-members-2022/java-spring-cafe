package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.controller.request.UserJoinRequest;
import com.ttasjwi.cafe.controller.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입 -> 성공")
    void joinSuccessTest() {
        // given
        UserJoinRequest userJoinRequest =
                UserJoinRequest.builder()
                        .userName("test-user1")
                        .userEmail("test-user1@gmail.com")
                        .password("1234")
                        .regDate(LocalDate.now())
                        .build();

        // when
        userService.join(userJoinRequest);
        UserResponse findUserResponse = userService.findByUserName(userJoinRequest.getUserName());

        // then
        assertThat(findUserResponse.getUserName()).isEqualTo(userJoinRequest.getUserName());
    }

    @Test
    @DisplayName("이름 중복 회원가입 -> 실패")
    void joinDuplicatedUserNameTest() {
        //given
        UserJoinRequest userJoinRequest1 =
                UserJoinRequest.builder()
                        .userName("test-user1")
                        .userEmail("test-user1@gmail.com")
                        .password("1234")
                        .regDate(LocalDate.now())
                        .build();

        UserJoinRequest userJoinRequest2 =
                UserJoinRequest.builder()
                        .userName(userJoinRequest1.getUserName())
                        .userEmail("test-user2@gmail.com")
                        .password("1234")
                        .regDate(LocalDate.now())
                        .build();

        userService.join(userJoinRequest1);

        //when & then
        assertThatThrownBy(() -> userService.join(userJoinRequest2))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("이메일 중복 회원가입 -> 실패")
    void joinDuplicatedUserEmailTest() {
        //given
        UserJoinRequest userJoinRequest1 =
                UserJoinRequest.builder()
                        .userName("test-user1")
                        .userEmail("test-user1@gmail.com")
                        .password("1234")
                        .regDate(LocalDate.now())
                        .build();

        UserJoinRequest userJoinRequest2 =
                UserJoinRequest.builder()
                        .userName("tests-user2")
                        .userEmail(userJoinRequest1.getUserEmail())
                        .password("1234")
                        .regDate(LocalDate.now())
                        .build();

        userService.join(userJoinRequest1);

        //when & then
        assertThatThrownBy(() -> userService.join(userJoinRequest2))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("존재하지 않는 회원 이름으로 조회 -> 실패")
    void findByUserNameFailTest() {
        // given
        String findUserName = "spring";

        // when & then
        assertThatThrownBy(() -> userService.findByUserName(findUserName))
                .isInstanceOf(NoSuchElementException.class);
    }

}
