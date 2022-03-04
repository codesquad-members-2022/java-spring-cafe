package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.UserForm;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserService 클래스")
class UserServiceTest {

    UserService userService;
    MemoryUserRepository memoryUserRepository;

    @BeforeEach
    void setup() {
        memoryUserRepository = new MemoryUserRepository();
        userService = new UserService(memoryUserRepository);
    }

    @AfterEach
    void clear() {
        memoryUserRepository.clear();
    }

    @Nested
    @DisplayName("join 메소드는")
    class Describe_join {

        @Nested
        @DisplayName("만약 리포지토리 없는 User가 주어진다면")
        class Context_with_user {

            @Test
            @DisplayName("user 객체를 리포지토리에 저장하고 Id를 리턴한다.")
            void it_save_user_and_return_userId() {
                User user = new User(new UserForm("testId", "1234", "haha", "test@gmail.com"));

                Long id = userService.join(user);

                assertThat(id).isEqualTo(user.getId());
            }
        }

        @Nested
        @DisplayName("만약 리포지토리 중복되는 User가 주어진다면")
        class Context_with_duplicate_user {

            @Test
            @DisplayName("IllegalArgumentException 예외를 던진다.")
            void it_throw_IllegalArgumentException() {
                User user1 = new User(new UserForm("testId", "1234", "haha", "test@gmail.com"));
                User user2 = new User(new UserForm("testId", "1234", "haha", "test@gmail.com"));

                userService.join(user1);

                assertThatThrownBy(() -> userService.join(user2)).isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayName("findUser 메소드는")
    class Describe_findUser {

        @Nested
        @DisplayName("만약 리포지토리에 존재하는 userId가 주어진다면")
        class Context_with_user {

            @Test
            @DisplayName("해당 유저 객체를 반환한다.")
            void it_return_user() {
                User user1 = new User(new UserForm("testId1", "1234", "haha1", "test@gmail.com"));
                User user2 = new User(new UserForm("testId2", "1234", "haha2", "test@gmail.com"));
                userService.join(user1);
                userService.join(user2);

                User sut = userService.findUser("testId1");

                assertThat(sut).isEqualTo(user1);
            }
        }

        @Nested
        @DisplayName("만약 리포지토리에 존재하지 않는 userId가 주어진다면")
        class Context_with_not_exist_userId {

            @Test
            @DisplayName("IllegalArgumentException 예외를 던진다.")
            void it_throw_IllegalArgumentException() {
                User user1 = new User(new UserForm("testId1", "1234", "haha1", "test@gmail.com"));
                userService.join(user1);

                assertThatThrownBy(() -> userService.findUser("TEST")).isInstanceOf(IllegalArgumentException.class);
            }
        }

    }

    @Nested
    @DisplayName("findUsers 메소드는")
    class Describe_findUsers {

        @Nested
        @DisplayName("만약 리포지토리에 2명의 user가 존재하고 호출된다면")
        class Context_without_parameter {

            @Test
            @DisplayName("2명의 user 리스트를 리턴한다.")
            void it_return_users() {
                User user1 = new User(new UserForm("testId1", "1234", "haha1", "test@gmail.com"));
                User user2 = new User(new UserForm("testId2", "1234", "haha2", "test@gmail.com"));
                userService.join(user1);
                userService.join(user2);

                List<User> sut = userService.findUsers();

                assertThat(sut.size()).isEqualTo(2);
            }
        }

        @Nested
        @DisplayName("만약 리포지토리에 user가 존재하지 않고 호출된다면")
        class Context_not_exist_users_and_without_parameter {

            @Test
            @DisplayName("비어있는 리스트를 리턴한다.")
            void it_return_users() {

                List<User> sut = userService.findUsers();

                assertThat(sut).isEmpty();
            }
        }
    }

}

