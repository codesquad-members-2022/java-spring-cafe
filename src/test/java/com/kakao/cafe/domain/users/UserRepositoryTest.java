package com.kakao.cafe.domain.users;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        @DisplayName("User 의 id 가 없고")
        class InsertTest {
            @Test
            @DisplayName("사용자 정보가 정상적으로 들어왔을 경우, 등록에 성공한다.")
            @Transactional
            @Rollback
            void insert_Success() {
                // given
                User user = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(user);
                repository.findAll()
                        .ifPresentOrElse(
                                users -> assertThat(users.get(0).getUserId()).isEqualTo(user.getUserId()),
                                Assertions::fail);
            }

            @Test
            @DisplayName("userId 가 다른 두 사용자를 저장하는 경우, 등록에 성공한다.")
            @Transactional
            @Rollback
            void differentUserId_Success() {
                // given
                User user1 = new User.Builder()
                        .setUserId("Jay")
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

                // then
                repository.save(user1);
                repository.save(user2);
                repository.findAll()
                        .ifPresentOrElse(
                                users -> assertThat(users).size().isEqualTo(2),
                                Assertions::fail);
            }

            @Test
            @DisplayName("userId 가 중복된 두 사용자를 저장하는 경우, 등록에 실패한다.")
            void duplicatedUserId_Success() {
                // given
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

                // then
                repository.save(user1);
                repository.save(user2);
                repository.findAll()
                        .ifPresentOrElse(
                                users -> assertThat(users).size().isEqualTo(1),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 userId 가 빠졌을 경우, 등록에 실패한다.")
            void userIdNull_throwSQLException() {
                // given
                User user = new User.Builder()
                        .setUserId(null)
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(user);
                repository.findAll()
                        .ifPresentOrElse(
                                users -> assertThat(users).size().isEqualTo(0),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 Passwd 가 빠졌을 경우, 등록에 실패한다.")
            void passwdNull_throwSQLException() {
                // given
                User user = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd(null)
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(user);
                repository.findAll()
                        .ifPresentOrElse(
                                users -> assertThat(users).size().isEqualTo(0),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 name 이 빠졌을 경우, 등록에 실패한다.")
            void nameNull_throwSQLException() {
                // given
                User user = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName(null)
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(user);
                repository.findAll()
                        .ifPresentOrElse(
                                users -> assertThat(users).size().isEqualTo(0),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 email 이 빠졌을 경우, 등록에 실패한다.")
            void emailNull_throwSQLException() {
                // given
                User user = new User.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail(null)
                        .build();

                // then
                repository.save(user);
                repository.findAll()
                        .ifPresentOrElse(
                                users -> assertThat(users).size().isEqualTo(0),
                                Assertions::fail);
            }
        }

    }

    @Nested
    @DisplayName("findById 메소드는")
    class FindByIdTest {

        @Test
        @DisplayName("1명의 User 가 있을때, 저장된 id 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByIdSuccess() {
            // given
            User user = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            Long savedId = repository.save(user).orElseThrow();

            // then
            repository.findById(savedId)
                    .ifPresentOrElse(findUser -> {
                        assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
                        assertThat(findUser.getName()).isEqualTo(user.getName());
                        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
                    }, Assertions::fail);
        }

        @Test
        @DisplayName("1명의 User 가 있을때, 다른 id 로 조회하면 조회되지 않는다.")
        void oneUserSaved_findByIdFailed() {
            // given
            User user = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            Long savedId = repository.save(user).orElseThrow();

            // then
            repository.findById(savedId + 1)
                    .ifPresent(findUser -> Assertions.fail());

        }
    }

    @Nested
    @DisplayName("findByUserId 메소드는")
    class FindByUserIdTest {

        @Test
        @DisplayName("저장된 userId 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByUserIdSuccess() {
            // given
            String expectedUserId = "jwkim";
            User user = new User.Builder()
                    .setUserId(expectedUserId)
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            repository.save(user).orElseThrow();

            // then
            repository.findByUserId(expectedUserId)
                    .ifPresentOrElse(findUser -> {
                        assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
                        assertThat(findUser.getName()).isEqualTo(user.getName());
                        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
                    }, Assertions::fail);
        }

        @Test
        @DisplayName("저장된 userId 와 다른 userId 로 조회하면 조회되지 않는다.")
        void oneUserSaved_findByIdFailed() {
            // given
            String expectedUserId = "jwkim";
            User user = new User.Builder()
                    .setUserId(expectedUserId)
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            repository.save(user).orElseThrow();

            // then
            repository.findByUserId(expectedUserId + "_hello")
                    .ifPresent(findUser -> Assertions.fail());

        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class FindALLTest {

        @Test
        @DisplayName("1명의 User 가 있을때, 길이가 1개인 User 리스트를 반환한다.")
        void oneUserSaved_findAllReturnList_size_1 () {
            // given
            User user = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            repository.save(user);
            List<User> users = repository.findAll().orElseThrow();

            // then
            assertThat(users).size().isEqualTo(1);

            // when
            User findUser = users.get(0);

            // then
            assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
            assertThat(findUser.getName()).isEqualTo(user.getName());
            assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
        }

        @Test
        @DisplayName("2명의 User 가 있을때, 길이가 2개인 User 리스트를 반환한다.")
        void twoUserSaved_findAllReturnList_size_2() {
            // given
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

            // when
            repository.save(user1);
            repository.save(user2);
            List<User> users = repository.findAll().orElseThrow();

            // then
            assertThat(users).size().isEqualTo(2);

            // when
            User findUser1 = users.get(0);
            User findUser2 = users.get(1);

            // then
            assertThat(findUser1.getUserId()).isEqualTo(user1.getUserId());
            assertThat(findUser1.getName()).isEqualTo(user1.getName());
            assertThat(findUser1.getEmail()).isEqualTo(user1.getEmail());
            assertThat(findUser2.getUserId()).isEqualTo(user2.getUserId());
            assertThat(findUser2.getName()).isEqualTo(user2.getName());
            assertThat(findUser2.getEmail()).isEqualTo(user2.getEmail());
        }

        @Test
        @DisplayName("User 가 없을때, 비어있는 리스트를 반환한다.")
        void notSaved_findAllReturnEmpty() {
            // when
            List<User> users = repository.findAll().orElseThrow();

            // then
            assertThat(users).size().isEqualTo(0);
        }
    }
}
