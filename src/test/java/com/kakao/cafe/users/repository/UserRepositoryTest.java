package com.kakao.cafe.users.repository;

import com.kakao.cafe.users.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserRepository 통합 테스트")
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

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
                User expectedUser = getIdNullUser();

                // act
                User savedUser = repository.save(expectedUser).orElseThrow();

                // assert
                assertThat(savedUser).isEqualTo(expectedUser);
            }

            @Test
            @DisplayName("userId 가 다른 두 사용자를 저장하는 경우, 등록에 성공한다.")
            void differentTwoId_insertSuccess() {
                // arrange
                User expectedUser1 = getIdNullUser("jay");
                User expectedUser2 = getIdNullUser("jwkim");

                // act
                User savedUser1 = repository.save(expectedUser1).orElseThrow();
                User savedUser2 = repository.save(expectedUser2).orElseThrow();

                // assert
                assertThat(savedUser1).isEqualTo(expectedUser1);
                assertThat(savedUser2).isEqualTo(expectedUser2);
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
            User expectedUser = getIdNullUser("jwkim");
            User savedUser = repository.save(expectedUser).orElseThrow();

            // act
            Optional<User> findResult = repository.findById(savedUser.getId());

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
            User expectedUser = getIdNullUser(expectedUserId);
            repository.save(expectedUser).orElseThrow();

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
            String unsavedId = "jwkim";

            // assert
            repository.findByUserId(unsavedId)
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
            User expectedUser = getIdNullUser();
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
            User user1 = getIdNullUser("jwkim1");
            User user2 = getIdNullUser("jwkim2");
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

    private User getIdNullUser() {
        return getIdNullUser("jwkim");
    }

    private User getIdNullUser(String userId) {
        return new User.Builder()
                .setUserId(userId)
                .setPasswd("1234")
                .setName("김진완")
                .setEmail("wlsdhks0423@naver.com")
                .build();
    }
}
