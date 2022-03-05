package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {
    UserServiceImpl userService;
    MemoryUserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserServiceImpl(userRepository);
    }

    @AfterEach
    public void afterEach() {
        userRepository.clearStore();
    }

    @Test
    @DisplayName("올바른 입력을 했을 때 회원가입이 성공적으로 이루어지는가")
    void joinSuccess() {
        //given
        User user = new User("honux", "호눅스", "1234a", "honux77@gmail.com");

        //when
        Long saveId = userService.join(user);

        //then
        User findUser = userService.findOne(user.getId()).get();
        Assertions.assertThat(user.getId()).isEqualTo(findUser.getId());
    }

    @Test
    @DisplayName("중복된 아이디일 때 예외처리가 제대로 이루어지는가")
    void joinDuplicate() {
        //given
        User user1 = new User("honux", "호눅스", "1234a", "honux77@gmail.com");

    }

    @Test
    void findUsers() {
    }
}