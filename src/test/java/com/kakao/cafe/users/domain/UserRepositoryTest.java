package com.kakao.cafe.users.domain;

import com.kakao.cafe.exception.repository.RequiredFieldNotFoundException;
import com.kakao.cafe.exception.repository.UniqueFieldDuplicatedException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("save 매소드는")
    class SaveTest {

        @Nested
        @DisplayName("회원의 id 가 없고")
        class InsertTest {
            @Test
            @DisplayName("사용자 정보가 정상적으로 들어왔을 경우, 등록에 성공한다.")
            @Transactional
            @Rollback
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
                User findUser = repository.findById(savedId).orElseThrow();

                // assert
                assertThat(findUser.getUserId()).isEqualTo(expectedUser.getUserId());
                assertThat(findUser.getPasswd()).isEqualTo(expectedUser.getPasswd());
                assertThat(findUser.getName()).isEqualTo(expectedUser.getName());
                assertThat(findUser.getEmail()).isEqualTo(expectedUser.getEmail());
            }

            @Test
            @DisplayName("userId 가 다른 두 사용자를 저장하는 경우, 등록에 성공한다.")
            @Transactional
            @Rollback
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
                User findUser1 = repository.findById(savedId1).orElseThrow();
                User findUser2 = repository.findById(savedId2).orElseThrow();

                // assert
                assertThat(findUser1.getUserId()).isEqualTo(expectedUser1.getUserId());
                assertThat(findUser1.getPasswd()).isEqualTo(expectedUser1.getPasswd());
                assertThat(findUser1.getName()).isEqualTo(expectedUser1.getName());
                assertThat(findUser1.getEmail()).isEqualTo(expectedUser1.getEmail());

                assertThat(findUser2.getUserId()).isEqualTo(expectedUser2.getUserId());
                assertThat(findUser2.getPasswd()).isEqualTo(expectedUser2.getPasswd());
                assertThat(findUser2.getName()).isEqualTo(expectedUser2.getName());
                assertThat(findUser2.getEmail()).isEqualTo(expectedUser2.getEmail());
            }

            @Test
            @DisplayName("userId 가 중복된 두 사용자를 저장하는 경우, 등록에 실패한다.")
            void duplicatedTwoId_throwUniqueFieldDuplicatedException() {
                // arrange
                User user1 = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();
                User user2 = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // assert
                assertThatThrownBy(()->{
                    repository.save(user1);
                    repository.save(user2);
                }).isInstanceOf(UniqueFieldDuplicatedException.class);
            }

            @Test
            @DisplayName("사용자 정보에서 userId 가 빠졌을 경우, 등록에 실패한다.")
            void userIdNull_throwRequiredFieldNotFoundException() {
                // arrange
                User user = new User.Builder()
                        .setUserId(null)
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // assert
                assertThatThrownBy(()->repository.save(user))
                        .isInstanceOf(RequiredFieldNotFoundException.class);
            }

            @Test
            @DisplayName("사용자 정보에서 Passwd 가 빠졌을 경우, 등록에 실패한다.")
            void passwdNull_throwRequiredFieldNotFoundException() {
                // arrange
                User user = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd(null)
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // assert
                assertThatThrownBy(()->repository.save(user))
                        .isInstanceOf(RequiredFieldNotFoundException.class);
            }

            @Test
            @DisplayName("사용자 정보에서 name 이 빠졌을 경우, 등록에 실패한다.")
            void nameNull_throwRequiredFieldNotFoundException() {
                // arrange
                User user = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName(null)
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // assert
                assertThatThrownBy(()->repository.save(user))
                        .isInstanceOf(RequiredFieldNotFoundException.class);
            }

            @Test
            @DisplayName("사용자 정보에서 email 이 빠졌을 경우, 등록에 실패한다.")
            void emailNull_throwRequiredFieldNotFoundException() {
                // arrange
                User user = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail(null)
                        .build();

                // assert
                assertThatThrownBy(()->repository.save(user))
                        .isInstanceOf(RequiredFieldNotFoundException.class);
            }
        }

    }

    @Nested
    @DisplayName("findById 메소드는")
    class FindByIdTest {

        @Test
        @DisplayName("1명의 User 가 있을때, 저장된 id 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByIdSuccess() {
            // arrange
            User user = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // act
            Long savedId = repository.save(user).orElseThrow();
            Optional<User> findResult = repository.findById(savedId);

            // assert
            findResult.ifPresentOrElse(
                    findUser -> {
                        assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
                        assertThat(findUser.getPasswd()).isEqualTo(user.getPasswd());
                        assertThat(findUser.getName()).isEqualTo(user.getName());
                        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
                    },
                    Assertions::fail);
        }

        @Test
        @DisplayName("저장되지 않은 회원의 id 로 조회하면 조회되지 않는다.")
        void oneUserSaved_findByIdFailed() {
            // arrange
            User user = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // act
            Long savedId = repository.save(user).orElseThrow();

            // assert
            repository.findById(savedId + 1)
                    .ifPresent(findUser -> Assertions.fail());

        }
    }

    @Nested
    @DisplayName("findByUserId 메소드는")
    class FindByUserIdTest {

        @Test
        @DisplayName("저장된 회원의 userId 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByUserIdSuccess() {
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
            repository.findByUserId(expectedUserId)
                    .ifPresentOrElse(findUser -> {
                        assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
                        assertThat(findUser.getPasswd()).isEqualTo(user.getPasswd());
                        assertThat(findUser.getName()).isEqualTo(user.getName());
                        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
                    }, Assertions::fail);
        }

        @Test
        @DisplayName("저장된 회원의 userId 와 다른 userId 로 조회하면 조회되지 않는다.")
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
    @DisplayName("findAll 메소드는")
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
            assertThat(savedUser.getUserId()).isEqualTo(expectedUser.getUserId());
            assertThat(savedUser.getPasswd()).isEqualTo(expectedUser.getPasswd());
            assertThat(savedUser.getName()).isEqualTo(expectedUser.getName());
            assertThat(savedUser.getEmail()).isEqualTo(expectedUser.getEmail());
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
            assertThat(savedUser1.getUserId()).isEqualTo(user1.getUserId());
            assertThat(savedUser1.getPasswd()).isEqualTo(user1.getPasswd());
            assertThat(savedUser1.getName()).isEqualTo(user1.getName());
            assertThat(savedUser1.getEmail()).isEqualTo(user1.getEmail());

            assertThat(savedUser2.getUserId()).isEqualTo(user2.getUserId());
            assertThat(savedUser2.getPasswd()).isEqualTo(user2.getPasswd());
            assertThat(savedUser2.getName()).isEqualTo(user2.getName());
            assertThat(savedUser2.getEmail()).isEqualTo(user2.getEmail());
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
