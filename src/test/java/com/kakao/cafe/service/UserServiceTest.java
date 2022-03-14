package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.Controller.dto.UserRequestDto;
import com.kakao.cafe.exception.DuplicateUserIdException;
import com.kakao.cafe.exception.NoMatchUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    private List<UserRequestDto> userRequestDtos;

    @BeforeEach
    void setup() {
        userRequestDtos = new ArrayList<>();
        userRequestDtos.add(new UserRequestDto("test1", "1234", "박우진", "abc@naver.com"));
        userRequestDtos.add(new UserRequestDto("test2", "1234", "김우진", "abc@google.com"));
        userRequestDtos.add(new UserRequestDto("test3", "1234", "최우진", "abc@kakao.com"));
    }

    @Test
    @DisplayName("회원가입용 데이터가 정상적으로 주어지면 회원가입이 성공한다")
    void joinSuccessTest() {
        // given

        // when
        User resultUser = userService.save(userRequestDtos.get(0));

        // then
        assertThat(resultUser.getUserId()).isEqualTo(userRequestDtos.get(0).getUserId());
    }

    @Test
    @DisplayName("이미 가입된 userId로 다시 가입을 시도하면 DuplicateUserIdException이 발생한다")
    void joinDuplicateTest() {
        // given
        for (UserRequestDto createDto : userRequestDtos) {
            userService.save(createDto);
        }

        // when
        DuplicateUserIdException e = assertThrows(DuplicateUserIdException.class,
                () -> userService.save(userRequestDtos.get(0)));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 가입된 ID입니다. 다른 ID로 가입해주세요.");
    }

    @Test
    @DisplayName("기존에 가입된 userId로 회원검색을 시도하면 해당하는 회원을 리턴한다")
    void findByUserIdTest() {
        // given
        userService.save(userRequestDtos.get(0));

        // when
        UserRequestDto userRequestDto = userService.findUserRequestDto(userRequestDtos.get(0).getUserId());
        User findUser = UserRequestDto.toEntity(userRequestDto);

        // then
        assertThat(findUser.getUserId()).isEqualTo(userRequestDto.getUserId());
    }

    @Test
    @DisplayName("가입되지 않은 userId로 회원검색을 시도하면 NoMatchUserException이 발생한다")
    void noMatchUserTest() {
        // given

        // when
        NoMatchUserException e = assertThrows(NoMatchUserException.class,
                () -> userService.findUserRequestDto("noUserId"));

        // then
        assertThat(e.getMessage()).isEqualTo("일치하는 회원이 없습니다. 확인 후 다시 시도해주세요.");
    }
}
