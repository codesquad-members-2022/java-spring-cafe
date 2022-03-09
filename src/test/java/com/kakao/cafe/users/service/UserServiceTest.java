package com.kakao.cafe.users.service;

import com.kakao.cafe.users.controller.dto.UserJoinRequest;
import com.kakao.cafe.users.domain.User;
import com.kakao.cafe.users.exception.UserDuplicatedException;
import com.kakao.cafe.users.exception.UserNotFountException;
import com.kakao.cafe.users.repository.MemoryUserRepository;
import com.kakao.cafe.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private final UserRepository userRepository = new MemoryUserRepository();
    private final UserService userService = new UserService(userRepository);

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("join 메소드는")
    class JoinTest {

        @Test
        @DisplayName("회원의 userId 가 중복되지 않으면, 회원가입에 성공한다.")
        void nonDuplicatedUserId_joinSuccess() {
            // arrange
            UserJoinRequest joinRequest =
                    new UserJoinRequest("jwkim","1234","김진완","wlsdhks0423@naver.com");

            // act
            Long savedId = userService.join(joinRequest);
            User savedUser = userRepository.findById(savedId).orElseThrow();

            // assert
            assertThat(savedUser.getUserId()).isEqualTo(joinRequest.getUserId());
            assertThat(savedUser.getPasswd()).isEqualTo(joinRequest.getPasswd());
            assertThat(savedUser.getName()).isEqualTo(joinRequest.getName());
            assertThat(savedUser.getEmail()).isEqualTo(joinRequest.getEmail());
        }

        @Test
        @DisplayName("회원의 userId 가 중복되면, 회원가입에 실패한다.")
        void duplicatedUserId_joinSuccess() {
            // arrange
            UserJoinRequest joinRequest =
                    new UserJoinRequest("jwkim","1234","김진완","wlsdhks0423@naver.com");

            // assert
            assertThatThrownBy(() -> {
                userService.join(joinRequest);
                userService.join(joinRequest);
            })
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
            UserJoinRequest joinRequest =
                    new UserJoinRequest("jwkim","1234","김진완","wlsdhks0423@naver.com");
            Long savedId = userService.join(joinRequest);

            // act
            User findUser = userService.findOne(savedId);

            // assert
            assertThat(findUser.getUserId()).isEqualTo(joinRequest.getUserId());
            assertThat(findUser.getPasswd()).isEqualTo(joinRequest.getPasswd());
            assertThat(findUser.getName()).isEqualTo(joinRequest.getName());
            assertThat(findUser.getEmail()).isEqualTo(joinRequest.getEmail());
        }

        @Test
        @DisplayName("저장되지 않은 회원의 id 를 조회하면, 예외가 발생한다.")
        void unSavedUserId_findFailed() {
            // arrange
            Long unsavedId = 1L;

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
            UserJoinRequest joinRequest =
                    new UserJoinRequest("jwkim","1234","김진완","wlsdhks0423@naver.com");
            userService.join(joinRequest);

            // act
            List<User> users = userService.findUsers();

            // assert
            assertThat(users).size().isEqualTo(1);

            User savedUser = users.get(0);
            assertThat(savedUser.getUserId()).isEqualTo(joinRequest.getUserId());
            assertThat(savedUser.getPasswd()).isEqualTo(joinRequest.getPasswd());
            assertThat(savedUser.getName()).isEqualTo(joinRequest.getName());
            assertThat(savedUser.getEmail()).isEqualTo(joinRequest.getEmail());
        }

        @Test
        @DisplayName("저장된 회원이 없으면, 빈 리스트를 반환한다.")
        void userNotExist_findUsersReturnsEmptyList() {
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
