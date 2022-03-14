package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.exception.user.UnMatchedPasswordException;
import com.kakao.cafe.repository.DomainRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import static com.kakao.cafe.message.UserMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @InjectMocks
    UserService userService;

    @Mock
    DomainRepository<User, String> repository;

    @Test
    @DisplayName("사용자 목록을 반환한다.")
    void searchAll() {
        // given
        List<User> users = new Vector<>();
        given(repository.findAll()).willReturn(users);

        // when
        List<User> result = userService.searchAll();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(users);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("사용자 목록에 존재하지 않는 ID의 가입 요청이 오면 사용자 등록에 성공한다.")
    void addSuccess() {
        // given
        NewUserParam newUserParam
                = new NewUserParam("user", "1234", "name", "user@gmail.com");
        User user = newUserParam.convertToUser();
        given(repository.findOne(user.getUserId()))
                .willReturn(Optional.empty());
        given(repository.save(user))
                .willReturn(Optional.of(user));

        // when
        User newUser = userService.add(newUserParam);

        // then
        assertThat(newUser).usingRecursiveComparison().isEqualTo(user);

        verify(repository).findOne(user.getUserId());
    }

    @Test
    @DisplayName("사용자 목록에 존재하는 ID의 가입 요청이 오면 사용자 등록에 실패한다.")
    void addFail() {
        NewUserParam newUserParam
                = new NewUserParam("user", "1234", "name", "user@gmail.com");
        given(repository.findOne(any(String.class)))
                .willReturn(Optional.ofNullable(newUserParam.convertToUser()));

        assertThatThrownBy(() -> userService.add(newUserParam))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessage(EXISTENT_ID_MESSAGE);

        verify(repository).findOne(any(String.class));
    }

    @Test
    @DisplayName("사용자 정보 수정 요청이 오면 현재 비밀번호와 입력된 비밀번호를 비교 후 같으면 저장소에 반영한다.")
    void updateSuccess() {
        ModifiedUserParam modifiedUserParam =
                new ModifiedUserParam(1, "userId", "1234", "1234",
                        "4321", "name", "user@gmail.com");

        User user = modifiedUserParam.convertToUser();
        given(repository.save(user)).willReturn(Optional.ofNullable(user));

        assertThat(userService.update(modifiedUserParam)).usingRecursiveComparison().isEqualTo(user);
        verify(repository).save(user);
    }

    @Test
    @DisplayName("사용자 정보 수정 요청이 오면 현재 비밀번호와 입력된 비밀번호를 비교 후 다르면 예외가 발생한다.")
    void updateFail() {
        ModifiedUserParam modifiedUserParam =
                new ModifiedUserParam(1, "userId", "1234", "4321",
                        "4321", "name", "user@gmail.com");

        assertThatThrownBy(() -> userService.update(modifiedUserParam))
                .isInstanceOf(UnMatchedPasswordException.class)
                .hasMessage(UNMATCHED_PASSWORD_MESSAGE);
    }

    @Test
    @DisplayName("인자로 받은 userId에 해당하는 사용자를 저장소에서 읽어와 반환한다")
    void searchSuccess() {
        // given
        String userId = "userId";
        User user = new User(1, userId, "password", "name", "email");
        given(repository.findOne(userId)).willReturn(Optional.ofNullable(user));

        // when
        User result = userService.search(userId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
        verify(repository).findOne(userId);
    }

    @Test
    @DisplayName("인자로 받은 userId에 해당하는 사용자가 없으면 예외가 발생한다.")
    void searchFail() {
        given(repository.findOne(anyString())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.search(anyString()))
                .isInstanceOf(NoSuchUserException.class)
                .hasMessage(NON_EXISTENT_ID_MESSAGE);

        verify(repository).findOne(anyString());
    }
}
