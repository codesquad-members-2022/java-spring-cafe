package com.kakao.cafe.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeUtils {

    public static Timestamp timestampOf(LocalDateTime time) {
        return time == null ? null : Timestamp.valueOf(time);
    }

    public static LocalDateTime dateTimeOf(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
