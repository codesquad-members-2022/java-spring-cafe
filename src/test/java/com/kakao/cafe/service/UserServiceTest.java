package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.controller.dto.SignUpRequestDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UserService 클래스")
class UserServiceTest {

    MemoryUserRepository userRepository = new MemoryUserRepository();
    UserService userService = new UserService(userRepository);

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class signUp_메소드는 {

        private final SignUpRequestDto givenNonDuplicatedRequest = new SignUpRequestDto(
            "new@test.user",
            "test user1",
            "password1");
        private final SignUpRequestDto givenDuplicatedRequest = new SignUpRequestDto(
            "already@present.user",
            "test user1",
            "password1");

        @BeforeEach
        void beforeEach() {
            userRepository.clear();
            User alreadyPresentUser = new User(
                "already@present.user",
                "already present user1",
                "password1");
            userRepository.save(alreadyPresentUser);
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_이메일을_가진_회원이_주어지면 {

            @Test
            @DisplayName("\"이미 존재하는 회원입니다.\"라는 IllegalStateException을 던진다")
            void 이미_존재하는_회원입니다_라는_IllegalStateException을_던진다() {
                assertThatThrownBy(() -> userService.signUp(givenDuplicatedRequest))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("이미 존재하는 회원입니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복되지_않은_이메일을_가진_회원이_주어지면 {

            @Test
            @DisplayName("주어진 회원을 저장하고 저장된 회원의 index를 리턴한다")
            void 주어진_회원을_저장하고_저장된_회원의_index를_리턴한다() {
                assertThat(userService.signUp(givenNonDuplicatedRequest)).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class findUser_메소드는 {

        private final String presentEmail = "already@present.user";
        private final String nonPresentEmail = "new@test.user";

        @BeforeEach
        void beforeEach() {
            userRepository.clear();
            User alreadyPresentUser = new User(
                "already@present.user",
                "already present user1",
                "password1");
            userRepository.save(alreadyPresentUser);
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_이메일이_주어지면 {

            @Test
            @DisplayName("해당 이메일을 가진 회원 객체를 리턴한다")
            void 해당_이메일을_가진_회원_객체를_리턴한다() {
                assertThat(userService.findUser(presentEmail).getEmail()).isEqualTo(presentEmail);
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_아이디가_주어지면 {

            @Test
            @DisplayName("\"해당 이메일을 가진 회원이 존재하지 않습니다.\"라는 NoSuchElementException 던진다")
            void 해당_이메일을_가진_회원이_존재하지_않습니다_라는_NoSuchElementException을_던진다() {
                assertThatThrownBy(() -> userService.findUser(nonPresentEmail))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("해당 이메일을 가진 회원이 존재하지 않습니다.");
            }
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class findUsers_메소드는 {

        @BeforeEach
        void setUp() {
            userRepository.clear();
            for (int i = 1; i <= 10; i++) {
                String email = "test" + i + "@user.com";
                String nickname = "test user" + i;
                String password = "password" + i;
                User user = new User(email, nickname, password);
                userRepository.save(user);
            }
        }

        @Test
        @DisplayName("가입한 회원들의 리스트를 리턴한다")
        void 가입한_회원들의_리스트를_리턴한다() {
            final List<User> result = userRepository.findAll();
            final int[] i = {1};
            result.forEach(user -> {
                assertThat(user.getNickname())
                    .isEqualTo("test user" + i[0]);
                i[0]++;
            });
        }
    }
}
