package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.UserCollectionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserCollectionRepositoryTest {

    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NAME = "user";
    private static final String USER_EMAIL = "user@example.com";

    private static final String OTHER_ID = "otherId";
    private static final String OTHER_PASSWORD = "secret";
    private static final String OTHER_NAME = "other";
    private static final String OTHER_EMAIL = "other@example.com";

    @InjectMocks
    private UserCollectionRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(
            new User(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL));
    }

    @Test
    @DisplayName("유저 객체를 저장한다")
    public void savePersistTest() {
        // then
        assertThat(user.getUserId()).isEqualTo(USER_ID);
        assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        assertThat(user.getName()).isEqualTo(USER_NAME);
        assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
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
        User other = new User(user);
        other.setName(OTHER_NAME);
        other.setEmail(OTHER_EMAIL);

        user.update(other);

        // when
        User updatedUser = userRepository.save(user);

        // then
        assertThat(updatedUser.getUserNum()).isEqualTo(user.getUserNum());
        assertThat(updatedUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(updatedUser.getName()).isEqualTo(other.getName());
        assertThat(updatedUser.getEmail()).isEqualTo(other.getEmail());
    }

    @Test
    @DisplayName("등록되지 않은 유저 ID 를 가진 유저 객체를 저장할 경우 예외를 반환한다")
    public void updateTest() {
        // given
        User other = new User(user);
        other.setUserId(OTHER_ID);

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> userRepository.save(other));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }
}
