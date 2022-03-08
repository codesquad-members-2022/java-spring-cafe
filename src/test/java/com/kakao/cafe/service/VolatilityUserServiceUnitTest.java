package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.repository.VolatilityUserRepositoryImpl;
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
    VolatilityUserRepositoryImpl userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.clear();
    }

    @Test
    void addUserSuccess() {
        // given
        User user = User.builder("user").build();
        given(userRepository.findOne(user.getUserId()))
                .willReturn(Optional.empty());
        given(userRepository.save(user))
                .willReturn(Optional.of(user));

        // when
        User newUser = userService.add(user);

        // then
        assertThat(newUser).isEqualTo(user);

        verify(userRepository).findOne(user.getUserId());
    }

    @Test
    void addUserFail() {
        User user = User.builder("user").build();
        given(userRepository.findOne(any(String.class)))
                .willReturn(Optional.ofNullable(user));

        assertThatThrownBy(() -> userService.add(user))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessage(EXISTENT_ID_MESSAGE);

        verify(userRepository).findOne(any(String.class));
    }
}
