package com.kakao.cafe.users.domain;

import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
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
        @Test
        void 회원아이디_가_빠졌을_경우_생성에_실패한다() {
            // assert
            assertThatThrownBy(() -> new User.Builder()
                    .setUserId(null)
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhls0423@naver.com")
                    .build())
                    .isInstanceOf(RequiredFieldNotFoundException.class)
                    .hasMessageContaining(RequiredFieldNotFound);
        }

        @Test
        void 비밀번호_가_빠졌을_경우_생성에_실패한다() {
            // assert
            assertThatThrownBy(() -> new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd(null)
                    .setName("김진완")
                    .setEmail("wlsdhls0423@naver.com")
                    .build())
                    .isInstanceOf(RequiredFieldNotFoundException.class)
                    .hasMessageContaining(RequiredFieldNotFound);
        }

        @Test
        void 이름_이_빠졌을_경우_생성에_실패한다() {
            // assert
            assertThatThrownBy(() -> new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName(null)
                    .setEmail("wlsdhls0423@naver.com")
                    .build())
                    .isInstanceOf(RequiredFieldNotFoundException.class)
                    .hasMessageContaining(RequiredFieldNotFound);
        }

        @Test
        void 이메일_이_빠졌을_경우_생성에_실패한다() {
            // assert
            assertThatThrownBy(() -> new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail(null)
                    .build())
                    .isInstanceOf(RequiredFieldNotFoundException.class)
                    .hasMessageContaining(RequiredFieldNotFound);
        }

        @Test
        void 생성일_은_입력하지않아도_현재시간을_기록한다() {
            // arrange
            LocalDateTime beforeCreate = LocalDateTime.now();

            // act
            User user = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhls0423@naver.com")
                    .build();
            LocalDateTime afterCreate = LocalDateTime.now();

            // assert
            assertThat(user.getCreatedDate()).isNotNull();
            assertThat(user.getCreatedDate()).isBetween(beforeCreate, afterCreate);
        }

        @Test
        void 수정일_은_입력하지않아도_현재시간을_기록한다() {
            // arrange
            LocalDateTime beforeCreate = LocalDateTime.now();

            // act
            User user = new User.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhls0423@naver.com")
                    .build();
            LocalDateTime afterCreate = LocalDateTime.now();

            // assert
            assertThat(user.getModifiedDate()).isNotNull();
            assertThat(user.getModifiedDate()).isBetween(beforeCreate, afterCreate);
        }
    }
}
