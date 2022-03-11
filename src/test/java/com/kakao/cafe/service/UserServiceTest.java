package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UserForm;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {
    UserService userService;
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach() {
        userRepository.clearStore();
    }

    @Test
    @DisplayName("올바른 입력을 했을 때 회원가입이 성공적으로 이루어지는가")
    void joinSuccess() {
        //given
        UserForm userForm = new UserForm("honux", "호눅스", "1234a", "honux77@gmail.com");

        //when
        int userIndex = userService.join(userForm);

        //then
        UserForm findUser = userService.findOneUser(userIndex-1);
        assertThat("honux").isEqualTo(findUser.getUserId());
    }

    @Test
    @DisplayName("중복된 아이디일 때 예외처리가 제대로 이루어지는가")
    void joinDuplicateUserId() {
        //given
        UserForm userForm1 = new UserForm("honux", "호눅스", "1234a", "honux77@gmail.com");
        UserForm userForm2 = new UserForm("honux", "크롱", "5678@", "crong1004@naver.com");

        //when
        userService.join(userForm1);

        //then
        assertThatThrownBy(() -> userService.join(userForm2))
                .hasMessage("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("모든 유저들이 잘 검색이 되는가")
    void findAll() {
        //given
        UserForm userForm1 = new UserForm("honux", "호눅스", "1234a", "honux77@gmail.com");
        UserForm userForm2 = new UserForm("crong", "크롱", "5678@", "crong1004@naver.com");
        UserForm userForm3 = new UserForm("ivy", "아이비", "1372345!@~", "ivy1234@kakao.com");

        //when
        userService.join(userForm1);
        userService.join(userForm2);
        userService.join(userForm3);

        //then
        assertThat(userService.findUsers().size()).isEqualTo(3);
        assertThat(userService.findUsers()).extracting(User::getUserId)
                .contains("honux", "crong", "ivy");
    }
}
