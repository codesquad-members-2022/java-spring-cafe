package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.UserJoinRequest;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("MemoryUserRepository 클래스")
class MemoryUserRepositoryTest {

    MemoryUserRepository repository;

    @BeforeEach
    void setup() {
        repository = new MemoryUserRepository();
    }

    @AfterEach
    void clear() {
        repository.clear();
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {

        @Nested
        @DisplayName("만약 user 객체가 주어진다면")
        class Context_with_user {

            @Test
            @DisplayName("user 객체를 저장한다.")
            void it_store_user_and_return_user() {
                User user = new User("testuserid", "1234", "haha", "test@gmail.com");

                User sut = repository.save(user);

                assertThat(sut.getUserId()).isEqualTo("testuserid");
                assertThat(sut.getPassword()).isEqualTo("1234");
                assertThat(sut.getName()).isEqualTo("haha");
                assertThat(sut.getEmail()).isEqualTo("test@gmail.com");
            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("파라미터 없이 실행한다면")
        class Context_without_parameter {

            @Test
            @DisplayName("모든 user 객체를 리스트로 리턴한다.")
            void it_store_user_and_return_user() {
                User user = new User("testuserid", "1234", "haha", "test@gmail.com");
                repository.save(user);
                User user2 = new User("testuserid2", "1234", "haha2", "test@gmail.com");
                repository.save(user2);

                List<User> sut = repository.findAll();

                assertThat(sut.size()).isEqualTo(2);
            }
        }
    }

    @Nested
    @DisplayName("findByUserId 메소드는")
    class Describe_findByUserId {

        @Nested
        @DisplayName("존재하는 유저의 id이 주어진다면")
        class Context_with_userId {

            @Test
            @DisplayName("해당 유저의 옵셔널 객체를 리턴한다.")
            void it_return_user() {
                User user = new User("testuserid", "1234", "haha", "test@gmail.com");
                repository.save(user);

                Optional<User> sut = repository.findByUserId("testuserid");

                assertThat(sut).contains(user);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 유저의 id가 주어진다면")
        class Context_with_not_exist_userId {
            @Test
            @DisplayName("비어있는 Optional을 리턴한다")
            void it_return_user() {
                User user = new User("testuserid", "1234", "haha", "test@gmail.com");
                repository.save(user);

                Optional<User> sut = repository.findByUserId("not_exist");

                assertThat(sut.isEmpty()).isTrue();
            }
        }
    }

}
