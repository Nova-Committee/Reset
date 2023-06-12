package cn.evole.mods.reset.task;

import cn.evole.mods.reset.Reset;
import cn.evole.mods.reset.config.BaseConfig;
import cn.evole.mods.reset.task.mode.Mode;
import cn.evole.mods.reset.task.mode.Time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:42
 * Name ScheduleTask
 * Description
 */

public abstract class ScheduleTask {
    protected String name;
    protected BaseConfig<?> config;
    protected boolean isExpired = false;
    protected Mode taskMode;
    protected LocalDateTime expiredTime;

    public ScheduleTask(String name) {
        this.name = name;
        taskMode = Mode.of(config.get(name).getTimePreset());
        expiredTime = getExpiredTime();
    }

    public abstract void runTask();

    public boolean isExpired() {

        if (LocalDateTime.now().isAfter(expiredTime)) {
            setExpired(true);
        }

        return isExpired;
    }

    public LocalDateTime getExpiredTime() {

        String time = config.get(name).getTimeNext();
        // 匹配格式: 2021-12-03, 12:00:00
        Pattern pattern = Pattern.compile("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29), (?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");

        if (pattern.matcher(time).find()) {
            return LocalDateTime.parse(time, formatter);
        } else {
            return taskMode.getExpiredTime();
        }
    }

    public void load() {
        Reset.instance.getTaskManager().addTask(getName(), this);
    }

    public void setExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public void taskComplete() {
        setExpired(false);

        if (taskMode instanceof Time.Preservable) {

            Time.Preservable preservableTask = (Time.Preservable) taskMode;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");

            String expiredTime = preservableTask.getExpiredTime().format(formatter);
            preservableTask.saveExpiredTime(name, expiredTime);
        }

        expiredTime = getExpiredTime();
    }

    public String getName() {
        return name;
    }
}
