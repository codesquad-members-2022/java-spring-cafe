package com.kakao.cafe.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class Time {

    private static final int THIS_YEAR = 2022;
    private static final int FEBURARY = 2;
    private static final Random random = new Random();

    private static LocalDateTime getRandomDate() {
        int date = random.nextInt(28) + 1;
        int hour = random.nextInt(23) + 1;
        int minute = random.nextInt(58) + 1;
        LocalDateTime dateTime = LocalDateTime.of(THIS_YEAR, FEBURARY, date, hour, minute);
        return dateTime;
    }

    public static Timestamp getRecentlyVisitedTime() {
        return Timestamp.valueOf(getRandomDate());
    }

    public static Timestamp changeLocalDatetimeToTimeStap(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime changeToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
