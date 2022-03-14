package com.kakao.cafe.users.domain;

import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import com.kakao.cafe.users.controller.dto.UserJoinRequest;
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
    }
}
