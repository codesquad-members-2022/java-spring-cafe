package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.DuplicateUserException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("Shine", "1234", "Shine", "Shine@gmail.com");
        user.setId(1L
        );
    }

    @Test
    public void saveTest() {
        // given
        given(userRepository.save(user)).willReturn(1L);

        // when
        Long userId = userService.register(user);

        // then
        assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저 가입시 중복 유저면 예외 반환")
    public void registerValidationTest() {
        // given
        User sameUser = new User("Shine", "1234", "Shine", "Shine@gmail.com");
        given(userRepository.findByUserId(user.getUserId())).willReturn(Optional.of(user));

        // when, then
        assertThatThrownBy(() -> userService.register(sameUser)).isInstanceOf(DuplicateUserException.class);
    }

    @Test
    public void findAllUsersTest() {
        // given
        given(userRepository.findAll()).willReturn(Arrays.asList(user));

        // when
        List<User> users = userService.findUsers();

        // then
        assertThat(users).contains(user);
    }

    @Test
    public void findUserTest() {
        // given
        given(userRepository.findByUserId(any())).willReturn(Optional.of(user));

        // when
        User findUser = userService.findUserById(user.getUserId());

        // then
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조화하면 예외를 반환한다")
    public void findUserExceptionTest() {
        // given
        given(userRepository.findByUserId(any())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> userService.findUserById(any())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void updateTest() {
        // given
        User other = new User("Shine", "1234", "update", "update@gmail.com");
        other.setId(1L);

        given(userRepository.findByUserId(any())).willReturn(Optional.of(user));
        given(userRepository.update(user.getUserId(), other)).willReturn(true);

        // when
        assertThat(userService.userUpdate(other)).isTrue();
    }
}
