package cn.evole.mods.reset.task.mode;

import cn.evole.mods.reset.Reset;
import lombok.val;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:44
 * Name TimeMode
 * Description
 */

public class Time {
    public interface Preservable {

        default void saveExpiredTime(String path, String time) {
            val refresh = Reset.instance.getRefreshConfig().get(path);
            refresh.setTimeNext(time);
            Reset.instance.getRefreshConfig().add(path , refresh);
            Reset.instance.getRefreshConfig().save();
        }

        LocalDateTime getExpiredTime();
    }

    public static class Second extends Mode {
        Second(String config) {
            super(config);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            int second = Integer.parseInt(setting);
            return LocalDateTime.now().plusSeconds(second);
        }
    }
    public static class Minute extends Mode {
        Minute(String config) {
            super(config);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            int minute = Integer.parseInt(setting);
            return LocalDateTime.now().plusMinutes(minute);
        }
    }
    public static class Hour extends Mode {
        Hour(String config) {
            super(config);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            int hour = Integer.parseInt(setting);
            return LocalDateTime.now().plusHours(hour);
        }
    }
    public static class Day extends Mode implements Preservable {
        Day(String config) {
            super(config);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            int day = Integer.parseInt(setting);
            LocalDateTime expiredTime = LocalDateTime.now().with(localTime);
            if (LocalDateTime.now().isAfter(expiredTime)) {
                return expiredTime.plusDays(day);
            }
            return expiredTime;
        }
    }

    public static class Date extends Mode implements Preservable {
        public Date(String setting) {
            super(setting);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            String[] split = setting.split("-");
            int month = Integer.parseInt(split[0]);
            int day = Integer.parseInt(split[1]);
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, day);
            LocalDateTime expiredTime = LocalDateTime.of(date, localTime);
            if (LocalDateTime.now().isAfter(expiredTime)) {
                return expiredTime.plusYears(1);
            }
            return expiredTime;
        }
    }
    public static class Week extends Mode implements Preservable {
        Week(String config) {
            super(config);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            int dayOfWeek = Integer.parseInt(setting);
            LocalDateTime expiredTime = LocalDateTime.now()
                    .with(DayOfWeek.of(dayOfWeek))
                    .with(localTime);
            if (LocalDateTime.now().isAfter(expiredTime)) {
                return expiredTime.plusWeeks(1);
            }
            return expiredTime;
        }
    }
    public static class Month extends Mode implements Preservable {
        Month(String config) {
            super(config);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            int dayOfMonth = Integer.parseInt(setting);
            LocalDateTime expiredTime = LocalDateTime.now().withDayOfMonth(dayOfMonth).with(localTime);
            if (LocalDateTime.now().isAfter(expiredTime)) {
                return expiredTime.plusMonths(1);
            }
            return expiredTime;
        }
    }
    public static class Year extends Mode implements Preservable {
        Year(String config) {
            super(config);
        }
        @Override
        public LocalDateTime getExpiredTime() {
            int dayOfYear = Integer.parseInt(setting);
            LocalDateTime expiredTime = LocalDateTime.now().withDayOfYear(dayOfYear).with(localTime);
            if (LocalDateTime.now().isAfter(expiredTime)) {
                return expiredTime.plusYears(1);
            }
            return expiredTime;
        }
    }
}
