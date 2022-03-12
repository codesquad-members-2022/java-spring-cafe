package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.domain.User;
import com.ttasjwi.cafe.repository.MemoryUserRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(new MemoryUserRepository());
    }

    @Test
    @DisplayName("회원가입 -> 성공")
    void joinSuccessTest() {
        // given
        User user = new User("ttasjwi", "ttasjwi920@gmail.com", "1234");

        // when
        userService.join(user);
        User findUser = userService.findByUserName(user.getUserName());

        // then
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("이름 중복 회원가입 -> 실패")
    void joinDuplicatedUserNameTest() {
        //given
        User user1 = new User("ttasjwi", "ttasjwi920@gmail.com", "1234");

        User user2 = new User();
        user2.setUserName(user1.getUserName());
        userService.join(user1);

        //when & then
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThatThrownBy(()->userService.join(user2))
                .hasMessageContaining("중복되는 이름")
                .isInstanceOf(IllegalStateException.class);

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("이메일 중복 회원가입 -> 실패")
    void joinDuplicatedUserEmailTest() {
        //given
        User user1 = new User("ttasjwi", "ttasjwi920@gmail.com", "1234");

        User user2 = new User();
        user2.setUserName("honux");
        user2.setUserEmail(user1.getUserEmail());
        userService.join(user1);

        //when & then
        SoftAssertions softAssertions = new SoftAssertions();
        
        softAssertions.assertThatThrownBy(()->userService.join(user2))
                .hasMessageContaining("중복되는 이메일")
                .isInstanceOf(IllegalStateException.class);
        
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("존재하지 않는 회원 이름으로 조회 -> 실패")
    void findByUserNameFailTest() {
        // given
        String findUserName = "spring";

        // when & then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThatThrownBy(() -> userService.findByUserName(findUserName))
                .hasMessageContaining("그런 회원은 존재하지 않습니다.")
                .isInstanceOf(NoSuchElementException.class);

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("findAll 메서드 호출 -> 모든 사용자 List 반환")
    void findAllTest() {
        // given
        User user1 = new User("user1", "user1@gmail.com", "1234");
        User user2 = new User("user2", "user2@gmail.com", "5678");

        userService.join(user1);
        userService.join(user2);

        // when
        List<User> list = userService.findAll();
        int size = list.size();

        // then
        assertThat(size).isEqualTo(2);
    }
}