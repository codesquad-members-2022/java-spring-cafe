package com.kakao.cafe.users;

import com.kakao.cafe.users.domain.User;
import com.kakao.cafe.users.domain.UserRepository;
import com.kakao.cafe.users.exception.UserDuplicatedException;
import com.kakao.cafe.users.exception.UserNotFountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("join 메소드는")
    class JoinTest {

        @Test
        @DisplayName("회원의 userId 가 중복되지 않으면, 회원가입에 성공한다.")
        void nonDuplicatedUserId_joinSuccess() {
            // arrange
            User user = new User.Builder()
                    .setId(1L)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(userRepository.save(user)).thenReturn(Optional.of(user.getId()));
            when(userRepository.findByUserId(any())).thenReturn(Optional.empty()); // 중복된 회원이 없음

            // act
            Long id = userService.join(user);

            // assert
            assertThat(id).isEqualTo(user.getId());
        }

        @Test
        @DisplayName("회원의 userId 가 중복되면, 회원가입에 실패한다.")
        void duplicatedUserId_joinSuccess() {
            // arrange
            User user = new User.Builder()
                    .setId(1L)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(userRepository.findByUserId(any())).thenReturn(Optional.of(user)); // 중복된 회원 있음

            // assert
            assertThatThrownBy(() -> userService.join(user))
                    .isInstanceOf(UserDuplicatedException.class)
                    .hasMessageContaining("이미 존재하는 회원입니다.");
        }
    }

    @Nested
    @DisplayName("findOne 메소드는")
    class FindOneTest{

        @Test
        @DisplayName("저장된 회원의 id 를 조회하면, User 를 반환한다.")
        void savedUserExist_findSuccess() {
            // arrange
            Long savedId = 1L;
            User user = new User.Builder()
                    .setId(savedId)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(userRepository.findById(any())).thenReturn(Optional.of(user));

            // act
            User findUser = userService.findOne(savedId);

            // assert
            assertThatIsEqualToAllUserField(findUser, user);
        }

        @Test
        @DisplayName("저장되지 않은 회원의 id 를 조회하면, 예외가 발생한다.")
        void unSavedUserId_findFailed() {
            // arrange
            Long unsavedId = 1L;
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty()); // 중복된 회원 있음

            // assert
            assertThatThrownBy(()->userService.findOne(unsavedId))
                    .isInstanceOf(UserNotFountException.class)
                    .hasMessageContaining("회원을 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("findUsers 메소드는")
    class FindUsersTest{
        @Test
        @DisplayName("회원이 저장되어 있으면, List<User> 를 반환한다.")
        void userExist_findUsersReturnsList() {
            // arrange
            User expectedUser = new User.Builder()
                    .setId(1L)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(userRepository.findAll()).thenReturn(Optional.of(List.of(expectedUser)));

            // act
            List<User> users = userService.findUsers();

            // assert
            assertThat(users).size().isEqualTo(1);
            assertThatIsEqualToAllUserField(users.get(0), expectedUser);
        }

        @Test
        @DisplayName("저장된 회원이 없으면, 빈 리스트를 반환한다.")
        void userNotExist_findUsersReturnsEmptyList() {
            // arrange
            when(userRepository.findAll()).thenReturn(Optional.of(Collections.emptyList())); // 중복된 회원 있음

            // act
            List<User> users = userService.findUsers();

            // assert
            assertThat(users).size().isEqualTo(0);
        }
    }

    private void assertThatIsEqualToAllUserField(User actual, User expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getPasswd()).isEqualTo(expected.getPasswd());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getCreatedDate()).isEqualTo(expected.getCreatedDate());
        assertThat(actual.getModifiedDate()).isEqualTo(expected.getModifiedDate());
    }

}
