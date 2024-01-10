package com.simple.board.domain.auditing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeCalculator {

    public static String calculate(LocalDateTime dateTime){
        LocalDateTime now = LocalDateTime.now();

        long years = ChronoUnit.YEARS.between(dateTime, now);
        if (years > 0) {
            return years + "년 전";
        }

        long months = ChronoUnit.MONTHS.between(dateTime, now);
        if (months > 0) {
            return months + "개월 전";
        }

        long days = ChronoUnit.DAYS.between(dateTime, now);
        if (days > 0) {
            return days + "일 전";
        }

        long hours = ChronoUnit.HOURS.between(dateTime, now);
        if (hours > 0) {
            return hours + "시간 전";
        }

        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        if (minutes > 0) {
            return minutes + "분 전";
        }

        return ChronoUnit.SECONDS.between(dateTime, now)+"초 전";
    }
}
