package com.kakao.cafe.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringValidator {
	/*
		라이브러리 마다 변경사항을 겪어보면서, Util 로 공통적으로 쓰게 하자고 생각했습니다.
		StringUtils.isNullOrEmpty();  // org.h2.util
		StringUtils.isEmpty()   //  org.springframework.util - deprecated
	 */
	public static Boolean isNullOrBlank(String text) {
		return StringUtils.isEmpty(text);
	}
}
