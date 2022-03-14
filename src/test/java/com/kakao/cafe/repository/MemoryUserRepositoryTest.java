package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.domain.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MemoryUserRepository 클래스")
class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class save_메소드는 {

        private final User givenUser = new User(
            "new@test.user",
            "test user1",
            "password1");

        @BeforeEach
        void setUp() {
            repository.clear();
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 새로운_User_객체가_주어지면 {

            @Test
            @DisplayName("주어진 객체를 저장하고 저장된 객체의 인덱스를 리턴한다")
            void 주어진_객체를_저장하고_저장된_객체의_인덱스를_리턴한다() {
                assertThat(repository.save(givenUser)).isEqualTo(0);
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 기존에_존재하는_User_객체가_주어지면 {

            User updatedUser = givenUser.update("new@test.user", "updatepassword1");

            @BeforeEach
            void setUp() {
                repository.clear();
            }

            @Test
            @DisplayName("기존에 저장되어있던 객체의 인덱스를 리턴한다")
            void 기존에_저장되어있던_객체의_인덱스를_리턴한다() {
                int expected = repository.save(givenUser);
                assertThat(repository.save(updatedUser)).isEqualTo(expected);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class findByEmail_메소드는 {

        private final User presentUser = new User(
            "already@present.user",
            "present user1",
            "password1");
        private final String presentEmail = "already@present.user";
        private final String nonPresentEmail = "new@test.user";

        @BeforeEach
        void setUp() {
            repository.clear();
            repository.save(presentUser);
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_이메일을_입력_받으면 {

            @Test
            @DisplayName("해당 이메일을 가진 User 객체를 찾아서 리턴한다")
            void 해당_이메일을_가진_User_객체를_찾아서_리턴한다() {
                final Optional<User> result = repository.findByEmail(presentEmail);
                assertThat(result.isPresent()).isTrue();
                assertThat(result.get().getEmail()).isEqualTo(presentEmail);
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_이메일을_입력_받으면 {

            @Test
            @DisplayName("결과를 반환하지 않는다")
            void 결과를_반환하지_않는다() {
                final Optional<User> result = repository.findByEmail(nonPresentEmail);
                assertThat(result.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class findAll_메소드는 {

        @BeforeEach
        void setUp() {
            repository.clear();
            for (int i = 1; i <= 10; i++) {
                String email = "test" + i + "@user.com";
                String nickname = "test user" + i;
                String password = "password" + i;
                User user = new User(email, nickname, password);
                repository.save(user);
            }
        }

        @Test
        @DisplayName("가입한 회원들의 리스트를 리턴한다")
        void 가입한_회원들의_리스트를_리턴한다() {
            final List<User> result = repository.findAll();
            assertThat(result.size()).isEqualTo(10);
        }
    }
}
