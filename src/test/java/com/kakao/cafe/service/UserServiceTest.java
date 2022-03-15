package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {
    UserService userService;
    MemoryUserRepository userRepository;

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
    @DisplayName("회원가입한 이름과 부여한 인덱스로 찾은 이름과 동일해야 한다")
    void join() {
        //given
        User user = new User();
        user.setName("Vans1");
        int saveIndex = userService.join(user);

        //when
        User findUser = userService.findOne(saveIndex);

        //then
        assertThat(user.getName()).isEqualTo(findUser.getName());
    }

    @Test
    @DisplayName("회원가입한 이름 n개와 저장된 회원의 n개가 동일해야한다.")
    void findMembers() {
        //given
        User user1 = new User();
        user1.setName("Vans1");
        userService.join(user1);
        User user2 = new User();
        user2.setName("Vans2");
        userService.join(user2);

        //when
        List<User> result = userService.findMembers();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원가입한 이름 갯수와 마지막 가입한 회원에게 부여된 index의 값이 동일해야 한다.")
    void findOne() {
        //given
        User user1 = new User();
        user1.setName("Vans1");
        User user2 = new User();
        user2.setName("Vans2");

        //when
        userService.join(user1);
        int result = userService.join(user2);

        //then
        assertThat(result).isEqualTo(2);
    }
}