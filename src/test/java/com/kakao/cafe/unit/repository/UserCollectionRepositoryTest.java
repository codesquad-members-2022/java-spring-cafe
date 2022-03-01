package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.UserCollectionRepository;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class UserCollectionRepositoryTest {

    @InjectMocks
    private UserCollectionRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(
            new User("userId", "password", "name", "email@example.com"));
    }

    @Test
    @DisplayName("유저 객체를 저장한다")
    public void savePersistTest() {
        // then
        assertThat(user.getUserId()).isEqualTo("userId");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email@example.com");
    }

    @Test
    @DisplayName("모든 유저 객체를 조회한다")
    public void findAllTest() {
        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users).containsExactly(user);
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저 객체를 조회한다")
    public void findByUserIdTest() {
        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        assertThat(findUser).hasValue(user);
    }

    @Test
    @DisplayName("유저 번호를 가지고 변경된 유저 객체를 저장한다")
    public void saveMergeTest() {
        // given
        User other = new User("userId", "password", "other", "other@example.com");
        User updatedUser = user.update(other);

        // when
        userRepository.save(updatedUser);

        // then
        assertThat(user.getUserNum()).isEqualTo(updatedUser.getUserNum());
        assertThat(user.getUserId()).isEqualTo(updatedUser.getUserId());
        assertThat(user.getPassword()).isEqualTo(updatedUser.getPassword());
        assertThat(user.getName()).isEqualTo(updatedUser.getName());
        assertThat(user.getEmail()).isEqualTo(updatedUser.getEmail());
    }

    @Test
    @DisplayName("유저 번호를 가지고, 등록되지 않은 유저 ID 를 가진 유저 객체를 저장할 경우 예외를 반환한다")
    public void updateTest() {
        // given
        User user = new User("other", "secret", "other", "other@example.com");
        user.setUserNum(1);

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> userRepository.save(user));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }
}
