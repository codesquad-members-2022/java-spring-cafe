package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.repository.Repository;
import org.junit.jupiter.api.DisplayName;
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
    Repository<User, String> repository;

    @Test
    @DisplayName("사용자 목록에 존재하지 않는 ID의 가입 요청이 오면 사용자 등록에 성공한다.")
    void addUserSuccess() {
        // given
        NewUserParam newUserParam = new NewUserParam("user", "1234","name", "user@gmail.com");
        User user = newUserParam.convertToUser();
        given(repository.findOne(user.getUserId()))
                .willReturn(Optional.empty());
        given(repository.save(user))
                .willReturn(Optional.of(user));

        // when
        User newUser = userService.add(newUserParam);

        // then
        assertThat(newUser).isEqualTo(user);

        verify(repository).findOne(user.getUserId());
    }

    @Test
    @DisplayName("사용자 목록에 존재하는 ID의 가입 요청이 오면 사용자 등록에 실패한다.")
    void addUserFail() {
        NewUserParam newUserParam = new NewUserParam("user", "1234","name", "user@gmail.com");

        given(repository.findOne(any(String.class)))
                .willReturn(Optional.ofNullable(newUserParam.convertToUser()));

        assertThatThrownBy(() -> userService.add(newUserParam))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessage(EXISTENT_ID_MESSAGE);

        verify(repository).findOne(any(String.class));
    }
}
