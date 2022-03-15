package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JdbcTemplateUserRepositoryTest {

    @Autowired UserRepository userRepository;

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

                User saveUser = userRepository.save(user);

                assertThat(saveUser.getUserId()).isEqualTo("testuserid");
                assertThat(saveUser.getPassword()).isEqualTo("1234");
                assertThat(saveUser.getName()).isEqualTo("haha");
                assertThat(saveUser.getEmail()).isEqualTo("test@gmail.com");
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
                userRepository.save(user);
                User user2 = new User("testuserid2", "1234", "haha2", "test@gmail.com");
                userRepository.save(user2);

                List<User> sut = userRepository.findAll();

                assertThat(sut).contains(user, user2);
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
                userRepository.save(user);

                Optional<User> sut = userRepository.findByUserId("testuserid");

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
                userRepository.save(user);

                Optional<User> sut = userRepository.findByUserId("not_exist");

                assertThat(sut.isEmpty()).isTrue();
            }
        }
    }
}
