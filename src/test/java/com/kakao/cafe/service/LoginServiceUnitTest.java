package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.repository.CrudRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LoginServiceUnitTest {

    @InjectMocks
    LoginService loginService;

    @Mock
    CrudRepository<User, String> repository;

    @Test
    @DisplayName("파라미터로 LoginParam 이 넘어오면 이를 사용하여 해당하는 사용자 정보를 불러온다.")
    void checkInfoSuccess() {
        // given
        String userId = "userId";
        String password = "password";
        LoginParam loginParam = new LoginParam(userId, password);
        User user = new User(1, userId, password, "name", "email");
        given(repository.findById(userId)).willReturn(Optional.of(user));

        // when
        User result = loginService.checkInfo(loginParam);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(user);

        verify(repository).findById(userId);
    }
}
