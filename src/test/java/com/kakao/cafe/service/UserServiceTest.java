package com.kakao.cafe.service;

import com.kakao.cafe.controller.UserForm;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User createUser(int number) {
        UserForm userForm = new UserForm("userId" + number,
                "userPassword" + number,
                "userName" + number,
                "userEmail" + number + "@naver.com");

        return new User(userForm);
    }

    @Test
    @DisplayName("회원가입을 하면, 유저가 저장소에 존재해야 한다.")
    void join() {
        //given
        User user1 = createUser(1);

        given(userRepository.findById(user1.getUserId()))
                .willReturn(Optional.of(user1));

        //when
        String user1Id = userService.join(user1);

        //then
        User foundUser = userService.findOne(user1Id);
        assertThat(user1.getUserId())
                .isEqualTo(foundUser.getUserId());
    }

    @Test
    @DisplayName("회원가입하는 유저의 Id가 중복된다면, 중복 오류 메시지가 나와야 한다.")
    void joinDuplicateUser() {
        //given
        User duplicatedUser = createUser(1);

        given(userRepository.findByName(duplicatedUser.getName()))
                .willReturn(Optional.of(duplicatedUser));

        //then
        assertThatThrownBy(() -> userService.join(duplicatedUser))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("Id를 통해 유저를 찾을 때, 저장소에서 유저를 찾아야 한다.")
    void findOne() {
        //given
        User user1 = createUser(1);
        User user2 = createUser(2);

        given(userRepository.findById(user1.getUserId()))
                .willReturn(Optional.of(user1));

        //when
        String user1Id = userService.join(user1);
        String user2Id = userService.join(user2);

        //then
        User foundUser = userService.findOne(user1Id);
        assertThat(user1.getUserId())
                .isEqualTo(foundUser.getUserId());
    }

    @Test
    @DisplayName("존재하지 않은 유저를 찾을 때, 오류 메시지가 나와야 한다.")
    void findNonexistentUser() {
        //when
        String nonexistentUser = "user1";

        given(userRepository.findById(nonexistentUser))
                .willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> userService.findOne(nonexistentUser))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("저장소에 있는 모든 유저를 불러올 수 있어야 한다.")
    void findUsers() {
        //given
        User user1 = createUser(1);
        User user2 = createUser(2);
        User user3 = createUser(3);
        
        given(userRepository.findAll())
                .willReturn(List.of(user1, user2, user3));

        //when
        userService.join(user1);
        userService.join(user2);
        userService.join(user3);

        //then
        List<User> users = userService.findUsers();
        assertThat(users).contains(user1, user2, user3);
    }
}
