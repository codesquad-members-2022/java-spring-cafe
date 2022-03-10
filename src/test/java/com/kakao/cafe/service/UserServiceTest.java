package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {
    UserService userService;
    MemoryUserRepository userRepository;

    @BeforeEach
    public void beforeEach(){
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach(){
        userRepository.clearStore();
    }

    @Test
    void 회원가입() {
        User user = new User();
        user.setName("hello");

        Long saveId = userService.join(user);

        User findUser = userService.findOne(saveId).get();
        assertThat(user.getName()).isEqualTo(findUser.getName());
    }

    @Test
    void 중복_회원_예외() {
        User firstUser = new User();
        firstUser.setName("meenzino");

        User secondUser = new User();
        secondUser.setName("meenzino");

        userService.join(firstUser);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(secondUser));


        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
