package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.controller.dto.SignUpRequestDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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

        private final User alreadyPresentUser = new User(
            "already@present.user",
            "already present user1",
            "password1");
        private final SignUpRequestDto givenNonDuplicatedEmailRequest = new SignUpRequestDto(
            "new@test.user",
            "test user1",
            "password1");
        private final SignUpRequestDto givenDuplicatedEmailRequest = new SignUpRequestDto(
            "already@present.user",
            "test user1",
            "password1");
        private final SignUpRequestDto givenNonDuplicatedNicknameRequest = new SignUpRequestDto(
            "new@test.user",
            "test user1",
            "password1");
        private final SignUpRequestDto givenDuplicatedNicknameRequest = new SignUpRequestDto(
            "new@test.user",
            "already present user1",
            "password1");

        @BeforeEach
        void beforeEach() {
            userRepository.clear();
            userRepository.save(alreadyPresentUser);
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_이메일을_가진_회원이_주어지면 {

            @Test
            @DisplayName("\"이미 존재하는 이메일입니다.\"라는 IllegalStateException을 던진다")
            void 이미_존재하는_이메일입니다_라는_IllegalStateException을_던진다() {
                assertThatThrownBy(() -> userService.signUp(givenDuplicatedEmailRequest))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("이미 존재하는 이메일입니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복되지_않은_이메일을_가진_회원이_주어지면 {

            @Test
            @DisplayName("주어진 회원을 저장하고 저장된 회원의 index를 리턴한다")
            void 주어진_회원을_저장하고_저장된_회원의_index를_리턴한다() {
                assertThat(userService.signUp(givenNonDuplicatedEmailRequest)).isEqualTo(1);
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_닉네임을_가진_회원이_주어지면 {

            @Test
            @DisplayName("\"이미 존재하는 닉네임입니다.\"라는 IllegalStateException을 던진다")
            void 이미_존재하는_닉네임입니다_라는_IllegalStateException을_던진다() {
                assertThatThrownBy(() -> userService.signUp(givenDuplicatedNicknameRequest))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("이미 존재하는 닉네임입니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복되지_않은_닉네임을_가진_회원이_주어지면 {

            @Test
            @DisplayName("주어진 회원을 저장하고 저장된 회원의 index를 리턴한다")
            void 주어진_회원을_저장하고_저장된_회원의_index를_리턴한다() {
                assertThat(userService.signUp(givenNonDuplicatedNicknameRequest)).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class findUser_메소드는 {

        private final User alreadyPresentUser = new User(
            "already@present.user",
            "already present user1",
            "password1");
        private final String presentNickname = "already present user1";
        private final String nonPresentNickname = "test user1";

        @BeforeEach
        void beforeEach() {
            userRepository.clear();
            userRepository.save(alreadyPresentUser);
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_닉네임이_주어지면 {

            @Test
            @DisplayName("해당 닉네임을 가진 회원 객체를 리턴한다")
            void 해당_닉네임을_가진_회원_객체를_리턴한다() {
                assertThat(userService.findUser(presentNickname).getUserId()).isEqualTo(
                    presentNickname);
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_닉네임이_주어지면 {

            @Test
            @DisplayName("\"해당 닉네임을 가진 회원이 존재하지 않습니다.\"라는 NoSuchElementException 던진다")
            void 해당_닉네임을_가진_회원이_존재하지_않습니다_라는_NoSuchElementException을_던진다() {
                assertThatThrownBy(() -> userService.findUser(nonPresentNickname))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("해당 닉네임을 가진 회원이 존재하지 않습니다.");
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
                assertThat(user.getUserId())
                    .isEqualTo("test user" + i[0]);
                i[0]++;
            });
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    class updateUser_메소드는 {

        private final User alreadyPresentUser = new User(
            "already@present.user",
            "updateTestUser",
            "password1");

        @BeforeEach
        void setUp() {
            userRepository.clear();
            userRepository.save(alreadyPresentUser);
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_않는_사용자의_업데이트_요청이_주어지면 {

            private final UserUpdateRequestDto nonPresentUserIdRequest = new UserUpdateRequestDto(
                "newTestUser",
                "updated@user.email",
                "wrongPassword1",
                "newPassword1"
            );

            @Test
            @DisplayName("\"해당 닉네임을 가진 회원이 존재하지 않습니다.\"라는 NoSuchElementException 던진다")
            void 해당_닉네임을_가진_회원이_존재하지_않습니다_라는_NoSuchElementException을_던진다() {
                assertThatThrownBy(() -> userService.updateUser(nonPresentUserIdRequest))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("해당 닉네임을 가진 회원이 존재하지 않습니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_사용자의_일치하지_않는_비밀번호가_입력된_업데이트_요청이_주어지면 {

            private final UserUpdateRequestDto wrongPasswordRequest = new UserUpdateRequestDto(
                "updateTestUser",
                "updated@user.email",
                "wrongPassword1",
                "newPassword1"
            );

            @Test
            @DisplayName("\"비밀번호가 일치하지 않습니다.\"라는 IllegalArgumentException 던진다")
                void 비밀번호가_일치하지_않습니다_라는_IllegalArgumentException을_던진다() {
                assertThatThrownBy(() -> userService.updateUser(wrongPasswordRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비밀번호가 일치하지 않습니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_사용자의_일치하는_비밀번호가_입력된_업데이트_요청이_주어지면 {

            private final UserUpdateRequestDto correctPasswordRequest = new UserUpdateRequestDto(
                "updateTestUser",
                "updated@user.email",
                "password1",
                "newPassword1"
            );

            @Test
            @DisplayName("사용자의 이메일과 비밀번호를 업데이트 한다.")
            void 사용자의_이메일과_비밀번호를_업데이트_한다() {
                User originalUser = userRepository.findByUserId(alreadyPresentUser.getUserId()).get();
                userService.updateUser(correctPasswordRequest);
                User result = userRepository.findByUserId(correctPasswordRequest.getUserId()).get();
                assertThat(result).isEqualTo(originalUser);
                assertThat(result.getEmail()).isEqualTo(correctPasswordRequest.getNewEmail());
            }
        }
    }
}
