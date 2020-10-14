package com.study.selfs.jdk8.time.duration;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;

/**
 * 可以用于时间的计算
 *
 */
public class DurationDemo {

    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,5);
        now.add(Calendar.HOUR_OF_DAY,2);
        Instant instant = Instant.ofEpochMilli(now.getTimeInMillis());
        Instant nowInstant = Instant.now();
        Duration between = Duration.between(nowInstant, instant);
        System.out.println("D: " + between.toDays()); // 相差多少天
        System.out.println("H: " + between.toHours()); // 相差多少个小时
        System.out.println("D:"+ between.toDays()+"; H: " + between.minus(Duration.ofDays(between.toDays())).toHours());
    }
}
