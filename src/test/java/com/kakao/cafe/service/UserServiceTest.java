package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService;
    MemoryUserRepository repository;
    Map<Long, User> store;
    long userCount = 0;

    // 각 테스트 전
    @BeforeEach
    void beforeEach() {
        repository = new MemoryUserRepository();
        userService = new UserService(repository);
        store = repository.getUserStore();
    }

    // 각 테스트 후
    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    @DisplayName("유저의 userName 과 createUser 이후 찾아낸 유저의 userName 이 동일해야 한다.")
    void 유저_생성_테스트() {
        // given
        User user = new User("testName", "1234", "juni8453@naver.com");

        // when
        userService.createUser(user);
        User foundUser = repository.findUser(user.getUserName()).get();

        // then
        assertThat(user.getUserName()).isEqualTo(foundUser.getUserName());

    }

    @Test
    @DisplayName("userName 이 같은 유저가 생성되면 예외가 발생해야 한다.")
    void 중복_유저_예외발생_테스트() {
        User user1 = new User("testName", "1234", "juni8453@naver.com");
        User user2 = new User("testName", "1234", "juni8453@naver.com");

        userService.createUser(user1);

        assertThrows(IllegalStateException.class, () -> userService.createUser(user2));
    }

    @Test
    @DisplayName("유저가 생성되면 생성된 유저의 userName 과 찾아낸 userName 이 동일해야 한다.")
    void 유저_찾기_테스트() {
        userCount = 0;

        User user = new User("testName", "1234", "juni8453@naver.com");
        user.setUserIdx(++userCount);
        store.put(user.getUserIdx(), user);

        User foundUser = userService.findUser(user.getUserName());

        assertThat(foundUser.getUserName()).isEqualTo(user.getUserName());
    }

    @Test
    @DisplayName("2개 이상의 유저가 생성되면 생성된 만큼의 유저의 userName 과 찾아낸 userName 이 동일해야 한다.")
    void 모든_유저_찾기_테스트() {
        userCount = 0;

        User user1 = new User("testName1", "1234", "juni8453@naver.com");
        User user2 = new User("testName2", "1234", "test@naver.com");
        user1.setUserIdx(++userCount);
        user2.setUserIdx(++userCount);

        store.put(user1.getUserIdx(), user1);
        store.put(user2.getUserIdx(), user2);

        List<User> allUsers = userService.findAllUsers();
        User foundUser1 = allUsers.get(0);
        User foundUser2 = allUsers.get(1);

        assertThat(foundUser1.getUserName()).isEqualTo(user1.getUserName());
        assertThat(foundUser1.getUserIdx()).isEqualTo(user1.getUserIdx());
        assertThat(foundUser2.getUserName()).isEqualTo(user2.getUserName());
        assertThat(foundUser2.getUserIdx()).isEqualTo(user2.getUserIdx());
    }
}