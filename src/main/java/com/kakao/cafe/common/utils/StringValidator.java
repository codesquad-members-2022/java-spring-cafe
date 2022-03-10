package com.kakao.cafe.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringValidator {
	public static Boolean isNullOrBlank(String text) {
		return StringUtils.isEmpty(text);
	}
}
