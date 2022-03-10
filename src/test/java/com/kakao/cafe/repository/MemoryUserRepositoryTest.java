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

    MemoryUserRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new MemoryUserRepository();
        User alreadyPresentUser = new User(
            "already@present.user",
            "already present user1",
            "password1");
        repository.save(alreadyPresentUser);
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class save_메소드는 {

        private final User givenUser = new User(
            "new@test.user",
            "test user1",
            "password1");

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class User_객체가_주어지면 {

            @Test
            @DisplayName("주어진 객체를 저장하고 저장된 객체를 리턴한다")
            void 주어진_객체를_저장하고_저장된_객체를_리턴한다() {
                assertThat(givenUser.getId())
                    .as("저장되지 않은 객체는 아이디가 null 이다")
                    .isNull();
                final User saved = repository.save(givenUser);
                assertThat(saved.getId())
                    .as("저장된 객체는 아이디가 추가되어 있다.")
                    .isNotNull();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class findById_메소드는 {

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_아이디를_입력_받으면 {

            @Test
            @DisplayName("해당 아이디를 가진 User 객체를 찾아서 리턴한다")
            void 해당_아이디를_가진_User_객체를_찾아서_리턴한다() {
                final Optional<User> result = repository.findById(1L);
                assertThat(result.isPresent()).isTrue();
                assertThat(result.get().getId()).isEqualTo(1L);
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_아이디를_입력_받으면 {

            @Test
            @DisplayName("Optional.EMPTY를 리턴한다")
            void Optional_EMPTY를_리턴한다() {
                final Optional<User> result = repository.findById(100L);
                assertThat(result.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class findByEmail_메소드는 {

        private final String presentEmail = "already@present.user";
        private final String nonPresentEmail = "new@test.user";

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

        @Test
        @DisplayName("가입한 회원들의 리스트를 리턴한다")
        void 가입한_회원들의_리스트를_리턴한다() {
            final List<User> result = repository.findAll();
            assertThat(result.size()).isEqualTo(1);
        }
    }
}
