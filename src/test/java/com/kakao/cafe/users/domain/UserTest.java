package com.kakao.cafe.users.domain;

import com.kakao.cafe.exception.domain.InvalidFieldLengthException;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import com.kakao.cafe.users.controller.dto.UserJoinRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("User 단위 테스트")
public class UserTest {

    private static final String RequiredFieldNotFound = "필수 정보가 없습니다.";

    @Nested
    @DisplayName("새로운 회원을 생성할 때")
    class CreateValidationTest {

        @Nested
        @DisplayName("회원가입 요청으로 생성할 경우")
        class WithJoinRequestTest {

            @Test
            void 회원아이디_가_없으면_생성에_실패한다() {
                // arrange
                UserJoinRequest joinRequest = getJoinRequest();
                joinRequest.setUserId(null);

                // assert
                assertThatThrownBy(() -> User.createWithJoinRequest(joinRequest))
                        .isInstanceOf(RequiredFieldNotFoundException.class)
                        .hasMessageContaining(RequiredFieldNotFound);
            }

            @Test
            void 비밀번호_가_없으면_생성에_실패한다() {
                // arrange
                UserJoinRequest joinRequest = getJoinRequest();
                joinRequest.setUserId(null);

                // assert
                assertThatThrownBy(() -> User.createWithJoinRequest(joinRequest))
                        .isInstanceOf(RequiredFieldNotFoundException.class)
                        .hasMessageContaining(RequiredFieldNotFound);
            }

            @Test
            void 이름_이_없으면_생성에_실패한다() {
                // arrange
                UserJoinRequest joinRequest = getJoinRequest();
                joinRequest.setUserId(null);

                // assert
                assertThatThrownBy(() -> User.createWithJoinRequest(joinRequest))
                        .isInstanceOf(RequiredFieldNotFoundException.class)
                        .hasMessageContaining(RequiredFieldNotFound);
            }

            @Test
            void 이메일_이_없으면_생성에_실패한다() {
                // arrange
                UserJoinRequest joinRequest = getJoinRequest();
                joinRequest.setUserId(null);

                // assert
                assertThatThrownBy(() -> User.createWithJoinRequest(joinRequest))
                        .isInstanceOf(RequiredFieldNotFoundException.class)
                        .hasMessageContaining(RequiredFieldNotFound);
            }

            @Test
            void 생성일_은_입력하지않아도_현재시간을_기록한다() {
                // arrange
                UserJoinRequest joinRequest = getJoinRequest();
                LocalDateTime beforeCreate = LocalDateTime.now();

                // act
                User user = User.createWithJoinRequest(joinRequest);
                LocalDateTime afterCreate = LocalDateTime.now();

                // assert
                assertThat(user.getCreatedDate()).isNotNull();
                assertThat(user.getCreatedDate()).isBetween(beforeCreate, afterCreate);
            }

            @Test
            void 수정일_은_입력하지않아도_현재시간을_기록한다() {
                // arrange
                UserJoinRequest joinRequest = getJoinRequest();
                LocalDateTime beforeCreate = LocalDateTime.now();

                // act
                User user = User.createWithJoinRequest(joinRequest);
                LocalDateTime afterCreate = LocalDateTime.now();

                // assert
                assertThat(user.getModifiedDate()).isNotNull();
                assertThat(user.getModifiedDate()).isBetween(beforeCreate, afterCreate);
            }

            @Test
            void 생성일_수정일을_입력하지_않아도_같은시간의_생성일_수정일을_기록한다() {
                // act
                UserJoinRequest joinRequest = getJoinRequest();
                User user = User.createWithJoinRequest(joinRequest);

                // assert
                assertThat(user.getCreatedDate()).isNotNull();
                assertThat(user.getModifiedDate()).isNotNull();
                assertThat(user.getCreatedDate()).isEqualTo(user.getModifiedDate());

            }

            private UserJoinRequest getJoinRequest() {
                return new UserJoinRequest(
                        "jwkim", "1234", "김진완", "wlsdhks0423@naver.com"
                );
            }
        }

        @Test
        void userId_의_길이가_20_이상이면_생성에_실패한다() {
            // arrange
            String userIdOverLength = "a".repeat(22);

            // assert
            Assertions.assertThatThrownBy(() -> new User.Builder()
                    .setUserId(userIdOverLength)
                    .setPasswd("")
                    .setName("")
                    .setEmail("")
                    .build())
                    .isInstanceOf(InvalidFieldLengthException.class);
        }

        @Test
        void passwd_의_길이가_42_이상이면_생성에_실패한다() {
            // arrange
            String passwdOverLength = "a".repeat(42);

            // assert
            Assertions.assertThatThrownBy(() -> new User.Builder()
                    .setUserId("")
                    .setPasswd(passwdOverLength)
                    .setName("")
                    .setEmail("")
                    .build())
                    .isInstanceOf(InvalidFieldLengthException.class);
        }

        @Test
        void name_의_길이가_40_이상이면_생성에_실패한다() {
            // arrange
            String nameOverLength = "a".repeat(42);

            // assert
            Assertions.assertThatThrownBy(() -> new User.Builder()
                    .setUserId("")
                    .setPasswd("")
                    .setName(nameOverLength)
                    .setEmail("")
                    .build())
                    .isInstanceOf(InvalidFieldLengthException.class);
        }

        @Test
        void email_의_길이가_320_이상이면_생성에_실패한다() {
            // arrange
            String emailOverLength = "a".repeat(322);

            // assert
            Assertions.assertThatThrownBy(() -> new User.Builder()
                    .setUserId("")
                    .setPasswd("")
                    .setName("")
                    .setEmail(emailOverLength)
                    .build())
                    .isInstanceOf(InvalidFieldLengthException.class);
        }
    }
}
