package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.UserDto;
import com.kakao.cafe.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository repository;
    @Autowired
    UserService service;

    @Test
    @DisplayName("회원가입")
    void join() {
        UserDto form = new UserDto("user", "1111", "hello@spring.com", "퐄퐄퐄");
        service.createUser(form);

        UserDto user = service.findSingleUser("user");

        assertThat(user.getUserId()).isEqualTo("user");
        assertThat(user.getName()).isEqualTo("퐄퐄퐄");

    }

    @Test
    @DisplayName("중복된 id로 회원가입 시 예외를 던진다.")
    void validateDuplicate() {
        //given
        UserDto form = new UserDto("forky", "1111", "hello@spring.com", "퐄퐄퐄");
        service.createUser(form);

        UserDto form2 = new UserDto("forky", "222", "hello@summer.com", "다른 포키");
        assertThatThrownBy(() -> service.createUser(form2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사용자 ID가 중복됩니다.");
    }
}
