package com.kakao.cafe.common.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeFormatterTest {
	@Test
	@DisplayName("숫자가 아닌 문자타입 파라미터 전달시 예외처리한다.")
	void type_long_by_not_number() {
		assertThatThrownBy(() -> TypeFormatter.toLongFromText("영"))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("숫자타입의 문자열 파라미터 전달시 숫자로 반환 확인한다.")
	void type_long_by_number() {
		Long actual = TypeFormatter.toLongFromText("1000");

		assertThat(actual).isNotZero();
	}
}
