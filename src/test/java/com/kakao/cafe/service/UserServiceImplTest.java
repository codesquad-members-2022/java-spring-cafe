package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        User findUser = userService.findOneUser(saveId).get();
        assertThat(user.getId()).isEqualTo(findUser.getId());
    }

    @Test
    @DisplayName("중복된 아이디일 때 예외처리가 제대로 이루어지는가")
    void joinDuplicateUserId() {
        //given
        User user1 = new User("honux", "호눅스", "1234a", "honux77@gmail.com");
        User user2 = new User("honux", "크롱", "5678@", "crong1004@naver.com");

        //when
        userService.join(user1);

        //then
        IllegalStateException e;
        e = assertThrows(IllegalStateException.class, () -> userService.join(user2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("모든 유저들이 잘 검색이 되는가")
    void findAll(){
        //given
        User user1 = new User("honux", "호눅스", "1234a", "honux77@gmail.com");
        User user2 = new User("crong", "크롱", "5678@", "crong1004@naver.com");
        User user3 = new User("ivy", "아이비", "1372345!@~", "ivy1234@kakao.com");

        //when
        userService.join(user1);
        userService.join(user2);
        userService.join(user3);

        //then
        assertThat(userService.findUsers()).contains(user1, user2, user3);
    }

    @Test
    @DisplayName("유저 아이디 입력 값이 없을 때 유저가 생성되지 않도록 처리되는가")
    void userIdNull() {
        //given
        User user = new User("", "호눅스", "1234a", "honux77@gmail.com");

        //when
        IllegalStateException e;
        e = assertThrows(IllegalStateException.class, () -> userService.join(user));

        //then
        assertThat(e.getMessage()).isEqualTo("유저 아이디 정보가 없습니다.");
    }

    @Test
    @DisplayName("유저 비밀번호 입력 값이 없을 때 유저가 생성되지 않도록 처리되는가")
    void userPasswordNull() {
        //given
        User user = new User("honux", "호눅스", "", "honux77@gmail.com");

        //when
        IllegalStateException e;
        e = assertThrows(IllegalStateException.class, () -> userService.join(user));

        //then
        assertThat(e.getMessage()).isEqualTo("유저 비밀번호 정보가 없습니다.");
    }

    @Test
    @DisplayName("유저 이름 입력 값이 없을 때 유저가 생성되지 않도록 처리되는가")
    void userNameNull() {
        //given
        User user = new User("honux", "", "1325443", "honux77@gmail.com");

        //when
        IllegalStateException e;
        e = assertThrows(IllegalStateException.class, () -> userService.join(user));

        //then
        assertThat(e.getMessage()).isEqualTo("유저 이름 정보가 없습니다.");
    }

    @Test
    @DisplayName("유저 이메일 입력 값이 없을 때 유저가 생성되지 않도록 처리되는가")
    void userEmailNull() {
        //given
        User user = new User("honux", "호눅스", "1325443", "");

        //when
        IllegalStateException e;
        e = assertThrows(IllegalStateException.class, () -> userService.join(user));

        //then
        assertThat(e.getMessage()).isEqualTo("유저 이메일 정보가 없습니다.");
    }


}