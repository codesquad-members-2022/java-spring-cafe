package com.kakao.cafe.common.utils;

public class MessageFormatter {
	public static String[] toMessageLines(String message) {
		if (message.contains(",")) {
			return message.split(",");
		}
		return new String[] {message};
	}
}
