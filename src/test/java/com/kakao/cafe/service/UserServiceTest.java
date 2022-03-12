package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach() {
        userRepository.clear();
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        //given
        User user = new User("test1", "테스트", "1234", "test@gmail.com");

        //when
        String saveUserId = userService.join(user);

        //then
        assertThat(saveUserId).isEqualTo(user.getUserId());
    }

    @Test
    @DisplayName("이미 가입 된 아이디명으로 회원가입을 시도하면 회원가입 할 수 없다.")
    void duplicate_userId_join() {
        //given
        User user1 = new User("test1", "테스트", "1234", "test@gmail.com");
        User user2 = new User("test1", "테스트22", "123456", "test22@gmail.com");
        userService.join(user1);

        //when
        assertThrows(IllegalStateException.class, () -> userService.join(user2));
    }

    @Test
    @DisplayName("전체 회원 목록 조회")
    void findUsers() {
        //given
        User user1 = new User("test1", "테스트1", "1234", "test1@gmail.com");
        User user2 = new User("test2", "테스트2", "1234", "test2@gmail.com");

        userService.join(user1);
        userService.join(user2);

        //when
        List<User> result = userService.findUsers();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 회원 정보 조회")
    void findOne() {
        //given
        User user1 = new User("test1", "테스트", "1234", "test1@gmail.com");

        userService.join(user1);

        //when
        User result = userService.findOne(user1.getUserId());

        //then
        assertThat(result.getUserId()).isEqualTo(user1.getUserId());
    }

    @Test
    @DisplayName("특정 회원이 존재하지 않을 경우 예외가 발생해야 한다.")
    void findOne_not_exist() {
        //given
        User user1 = new User("test1", "테스트", "1234", "test1@gmail.com");

        //when
        assertThrows(NoSuchElementException.class, () -> userService.findOne(user1.getUserId()));
    }
}
