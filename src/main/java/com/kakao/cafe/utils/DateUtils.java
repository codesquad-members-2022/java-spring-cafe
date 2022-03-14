package com.kakao.cafe.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 인스턴스화 방지
    private DateUtils() { }

    public static String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new IllegalArgumentException("날짜를 변환할 수 없습니다.");
        }

        return localDateTime.format(formatter);
    }
}
