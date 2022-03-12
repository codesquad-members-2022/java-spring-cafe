package com.kakao.cafe.common.utils;

import java.util.regex.Pattern;

import com.kakao.cafe.user.domain.User;

public class TypeConvertor {
	private static final String regex = "\\p{Digit}";
	public static final String ERROR_OF_TYPE_OF_NOT_NUMERIC = "the type is not numeric";

	public static String toTextFromLong(Long longNumber) {
		return String.valueOf(longNumber);
	}

	public static String toTextFromInt(int intNumber) {
		return String.valueOf(intNumber);
	}

	public static Long toLongFromText(String numberText) {
		if (!isNumeric(numberText)) {
			throw new IllegalArgumentException(ERROR_OF_TYPE_OF_NOT_NUMERIC);
		}
		return Long.parseLong(numberText);
	}

	private static boolean isNumeric(String numberText) {
		return Pattern.compile(regex).matcher(numberText).find();
	}
}
