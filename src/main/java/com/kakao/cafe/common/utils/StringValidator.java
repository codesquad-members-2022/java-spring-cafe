package com.kakao.cafe.common.utils;

import java.util.Objects;

public class StringValidator {
	public static Boolean isNullOrBlank(String text) {
		return (Objects.isNull(text) || text.isBlank());
	}
}
