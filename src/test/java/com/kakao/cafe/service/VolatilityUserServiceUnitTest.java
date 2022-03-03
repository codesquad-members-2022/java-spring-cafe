package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.repository.VolatilityUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.kakao.cafe.message.UserMessage.EXISTENT_ID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VolatilityUserServiceUnitTest {

    @InjectMocks
    VolatilityUserService userService;

    @Mock
    VolatilityUserRepository userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.clear();
    }

    @Test
    void addUserSuccess() {
        User user = User.builder("user").build();
        given(userRepository.selectUser(user.getUserId()))
                .willReturn(Optional.empty());

        User newUser = userService.addUser(user);

        verify(userRepository).selectUser(user.getUserId());
        assertThat(newUser).isEqualTo(user);
    }

    @Test
    void addUserFail() {
        User user = User.builder("user").build();
        given(userRepository.selectUser(any(String.class)))
                .willReturn(Optional.ofNullable(user));

        assertThatThrownBy(() -> userService.addUser(user))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessage(EXISTENT_ID_MESSAGE);
    }
}