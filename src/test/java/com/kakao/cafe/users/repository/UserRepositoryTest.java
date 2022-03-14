package com.kakao.cafe.users.repository;

import com.kakao.cafe.exception.repository.UniqueFieldDuplicatedException;
import com.kakao.cafe.users.domain.User;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserRepository 단위 테스트")
class UserRepositoryTest {

    private final UserRepository repository = new MemoryUserRepository();

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("회원을 저장할 때")
    class SaveTest {

        @Nested
        @DisplayName("회원의 id 가 없고")
        class InsertTest {

            private static final String UniqueFieldDuplicated = "중복된 필드가 존재합니다.";

            @Test
            @DisplayName("정보가 정상적으로 들어왔을 경우, 등록에 성공한다.")
            void insert_success() {
                // arrange
                User expectedUser = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // act
                Long savedId = repository.save(expectedUser).orElseThrow();
                expectedUser.setId(savedId);
                User findUser = repository.findById(savedId).orElseThrow();

                // assert
                assertThat(findUser).isEqualTo(expectedUser);
            }

            @Test
            @DisplayName("userId 가 다른 두 사용자를 저장하는 경우, 등록에 성공한다.")
            void differentTwoId_insertSuccess() {
                // arrange
                User expectedUser1 = new User.Builder()
                        .setUserId("Jay")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();
                User expectedUser2 = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // act
                Long savedId1 = repository.save(expectedUser1).orElseThrow();
                Long savedId2 = repository.save(expectedUser2).orElseThrow();
                expectedUser1.setId(savedId1);
                expectedUser2.setId(savedId2);
                User findUser1 = repository.findById(savedId1).orElseThrow();
                User findUser2 = repository.findById(savedId2).orElseThrow();

                // assert
                assertThat(findUser1).isEqualTo(expectedUser1);
                assertThat(findUser2).isEqualTo(expectedUser2);
            }
        }
    }

    @Nested
    @DisplayName("id 로 회원을 검색할 때")
    class FindByIdTest {

        @Test
        @DisplayName("저장된 id 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByIdSuccess() {
            // arrange
            User expectedUser = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // act
            Long savedId = repository.save(expectedUser).orElseThrow();
            expectedUser.setId(savedId);
            Optional<User> findResult = repository.findById(savedId);

            // assert
            findResult.ifPresentOrElse(
                    findUser -> assertThat(findUser).isEqualTo(expectedUser),
                    Assertions::fail);
        }

        @Test
        @DisplayName("저장되지 않은 id 로 조회하면 조회되지 않는다.")
        void oneUserSaved_findByIdFailed() {
            // assert
            repository.findById(1L)
                    .ifPresent(findUser -> Assertions.fail());
        }
    }

    @Nested
    @DisplayName("userId 로 회원을 검색할 때")
    class FindByUserIdTest {

        @Test
        @DisplayName("저장된 userId 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByUserIdSuccess() {
            // arrange
            String expectedUserId = "jwkim";
            User expectedUser = new User.Builder()
                    .setUserId(expectedUserId)
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();
            Long savedId = repository.save(expectedUser).orElseThrow();
            expectedUser.setId(savedId);

            // act
            Optional<User> findResult = repository.findByUserId(expectedUserId);

            // assert
            findResult.ifPresentOrElse(
                    findUser -> assertThat(findUser).isEqualTo(expectedUser),
                    Assertions::fail);
        }

        @Test
        @DisplayName("저장되지 않은 userId 로 조회하면 조회되지 않는다.")
        void oneUserSaved_findByIdFailed() {
            // arrange
            String expectedUserId = "jwkim";
            User user = new User.Builder()
                    .setUserId(expectedUserId)
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // act
            repository.save(user).orElseThrow();

            // assert
            repository.findByUserId(expectedUserId + "_hello")
                    .ifPresent(findUser -> Assertions.fail());

        }
    }

    @Nested
    @DisplayName("전체 회원 목록을 불러올 때")
    class FindALLTest {

        @Test
        @DisplayName("1명의 회원이 있으면, 길이가 1개인 회원 리스트를 반환한다.")
        void oneUserSaved_findAllReturnList_size_1 () {
            // arrange
            User expectedUser = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();
            repository.save(expectedUser);

            // act
            List<User> users = repository.findAll().orElseThrow();
            User savedUser = users.get(0);

            // assert
            assertThat(users).size().isEqualTo(1);
            assertThat(savedUser).isEqualTo(expectedUser);
        }

        @Test
        @DisplayName("2명의 User 가 있을때, 길이가 2개인 User 리스트를 반환한다.")
        void twoUserSaved_findAllReturnList_size_2() {
            // arrange
            User user1 = new User.Builder()
                    .setUserId("jwkim1")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();
            User user2 = new User.Builder()
                    .setUserId("jwkim2")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();
            repository.save(user1);
            repository.save(user2);

            // act
            List<User> users = repository.findAll().orElseThrow();
            User savedUser1 = users.get(0);
            User savedUser2 = users.get(1);

            // assert
            assertThat(users).size().isEqualTo(2);
            assertThat(savedUser1).isEqualTo(user1);
            assertThat(savedUser2).isEqualTo(user2);
        }

        @Test
        @DisplayName("User 가 없을때, 비어있는 리스트를 반환한다.")
        void notSaved_findAllReturnEmpty() {
            // act
            List<User> users = repository.findAll().orElseThrow();

            // assert
            assertThat(users).size().isEqualTo(0);
        }
    }
}
