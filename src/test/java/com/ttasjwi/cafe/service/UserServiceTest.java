package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.domain.user.MemoryUserRepository;
import com.ttasjwi.cafe.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        User user = User.builder()
                .userName("test-user1")
                .userEmail("test-user1@gmail.com")
                .password("1234")
                .build();

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
        User user1 = User.builder()
                .userName("test-user1")
                .userEmail("test-user1@gmail.com")
                .build();

        User user2 = User.builder()
                .userName(user1.getUserName())
                .userEmail("test-user2@gmail.com")
                .build();

        userService.join(user1);

        //when & then
        assertThatThrownBy(()->userService.join(user2))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("이메일 중복 회원가입 -> 실패")
    void joinDuplicatedUserEmailTest() {
        //given
        User user1 = User.builder()
                .userName("test-user1")
                .userEmail("test-user1@gmail.com")
                .build();

        User user2 = User.builder()
                .userName("test-user2")
                .userEmail(user1.getUserEmail())
                .build();

        userService.join(user1);

        //when & then
        assertThatThrownBy(()->userService.join(user2))
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