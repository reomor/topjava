package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable> boolean isBetween(T l, T start, T end) {
        return l.compareTo(start) >= 0 && l.compareTo(end) <= 0;
    }

    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenDate(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static void main(String[] args) {
        System.out.println(isBetweenDate(LocalDate.of(2017, 11, 11), LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 14)));
        System.out.println(isBetweenDate(LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 14)));
        System.out.println(isBetweenDate(LocalDate.of(2017, 11, 13), LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 14)));
        System.out.println(isBetweenDate(LocalDate.of(2017, 11, 14), LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 14)));
        System.out.println(isBetweenDate(LocalDate.of(2017, 11, 15), LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 14)));
    }
}
