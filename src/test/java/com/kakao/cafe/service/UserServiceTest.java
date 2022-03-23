package com.kakao.cafe.service;
import com.kakao.cafe.controller.UserForm;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserMemoryRepository;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserService userService;
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = new UserMemoryRepository();
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach() {
        userRepository.clearStore();
    }

    @Test
    void join() {
        // given
        User user = new User("abc123@gmail.com", "ABC", "abc");

        // when
        User saveUser = userService.join(UserForm.from(user));

        // then
        UserForm findUser = userService.findOneByEmail(user.getEmail());

        assertThat(UserForm.from(saveUser)).isEqualTo(findUser);
    }

    @Test
    void findAllUserForm() {
        List<UserForm> userFormList = new ArrayList<>();

        User user1 = new User("abc123@gmail.com", "ABC", "abc");
        User user2 = new User("def123@gmail.com", "DEF", "def");

        userFormList.add(UserForm.from(user1));
        userFormList.add(UserForm.from(user2));

        userService.join(UserForm.from(user1));
        userService.join(UserForm.from(user2));

        List<UserForm> result = userService.findAllUserForm();

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).equals(userFormList.get(i)));
        }
    }

    @Test
    void findOneByEmail() {
        User user = new User("abc123@gmail.com", "ABC", "abc");
        UserForm form = UserForm.from(user);
        userService.join(UserForm.from(user));

        assertThat(userService.findOneByEmail(user.getEmail()).equals(form));
    }

    @Test
    void findOneByUserId() {
        User user = new User("abc123@gmail.com", "ABC", "abc");
        UserForm form = UserForm.from(user);
        userService.join(UserForm.from(user));

        assertThat(userService.findOneByUserId(user.getUserId()).equals(form));
    }
}
