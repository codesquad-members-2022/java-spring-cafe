package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.collections.UserCollectionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserCollectionRepositoryTest {

    private final UserCollectionRepository userRepository = new UserCollectionRepository();

    User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build());
    }

    @Test
    @DisplayName("유저 객체를 저장한다")
    public void savePersistTest() {
        // then
        then(user.getUserId()).isEqualTo("userId");
        then(user.getPassword()).isEqualTo("userPassword");
        then(user.getName()).isEqualTo("userName");
        then(user.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("모든 유저 객체를 조회한다")
    public void findAllTest() {
        // when
        List<User> users = userRepository.findAll();

        // then
        then(users).containsExactly(user);
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저 객체를 조회한다")
    public void findByUserIdTest() {
        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        then(findUser).hasValue(user);
    }

    @Test
    @DisplayName("유저 번호를 가지고 변경된 유저 객체를 저장한다")
    public void saveMergeTest() {
        // given
        User changedUser = new User.Builder()
            .userNum(1)
            .userId("userId")
            .password("userPassword")
            .name("otherName")
            .email("other@example.com")
            .build();

        // when
        User updatedUser = userRepository.save(changedUser);

        // then
        then(updatedUser.getUserNum()).isEqualTo(1);
        then(updatedUser.getUserId()).isEqualTo("userId");
        then(updatedUser.getPassword()).isEqualTo("userPassword");
        then(updatedUser.getName()).isEqualTo("otherName");
        then(updatedUser.getEmail()).isEqualTo("other@example.com");
    }

    @Test
    @DisplayName("등록되지 않은 유저 ID 를 가진 유저 객체를 저장할 경우 예외를 반환한다")
    public void updateTest() {
        // given
        User changedUser = new User.Builder()
            .userNum(1)
            .userId("otherId")
            .password("userPassword")
            .name("otherName")
            .email("other@example.com")
            .build();

        // when
        Throwable throwable = catchThrowable(() -> userRepository.save(changedUser));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }
}
