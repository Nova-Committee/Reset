package cn.evole.mods.reset.task.mode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Pattern;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:43
 * Name TaskMod
 * Description
 */

public abstract class Mode {
    protected final String name;
    protected final String setting;

    protected LocalTime localTime;

    public Mode(String config) {

        if (config.contains(",")) {
            String[] split = config.split(",");
            Pattern pattern = Pattern.compile("^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");

            String localTimeFormat = split[1].trim();

            if (!pattern.matcher(localTimeFormat).find()) {
                throw new IllegalArgumentException("时间参数 " + localTimeFormat + " 异常, 请确认格式为 hh:mm:ss, 如 07:56:03");
            }

            localTime = LocalTime.parse(localTimeFormat);
            config = split[0];
        }

        name = config.split(":")[0];
        setting = config.split(":")[1];
    }

    public abstract LocalDateTime getExpiredTime();

    public static Mode of(String config) {

        if (config.startsWith("second")) return new Time.Second(config);
        if (config.startsWith("minute")) return new Time.Minute(config);
        if (config.startsWith("hour")) return new Time.Hour(config);
        if (config.startsWith("date")) return new Time.Date(config);
        if (config.startsWith("day")) return new Time.Day(config);
        if (config.startsWith("week")) return new Time.Week(config);
        if (config.startsWith("month")) return new Time.Month(config);
        if (config.startsWith("year")) return new Time.Year(config);

        throw new IllegalArgumentException("时间设置配置错误, 请检查配置文件");
    }
}
