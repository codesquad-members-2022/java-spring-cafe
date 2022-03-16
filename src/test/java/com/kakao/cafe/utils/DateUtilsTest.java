package com.kakao.cafe.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DateUtils 단위 테스트")
class DateUtilsTest {

    @Nested
    @DisplayName("formatDateTime 메소드는")
    class FormatDateTimeTest{

        @Test
        @DisplayName("yyyy-MM-dd HH:mm:ss 형식으로 LocalDateTime 을 문자열로 반환한다.")
        void successTest() {
            // arrange
            LocalDateTime datetime = LocalDateTime.of(2022, 3, 14, 18, 38, 0);
            String expected = "2022-03-14 18:38:00";

            // act
            String result = DateUtils.formatDateTime(datetime);

            // assert
            assertThat(result).isEqualTo(expected);
        }

        @Test
        void 입력값이_null_이면_예외가_발생한다() {
            // assert
            assertThatThrownBy(() -> DateUtils.formatDateTime(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("날짜를 변환할 수 없습니다.");
        }
    }

}