package cn.evole.mods.reset.task;

import cn.evole.config.bukkit.ConfigurationSection;
import cn.evole.mods.reset.Reset;
import cn.evole.mods.reset.util.Logger;

import java.util.*;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 17:57
 * Name TaskManager
 * Description
 */

public class TaskManager {
    private final Map<String, ScheduleTask> scheduleTasks = new HashMap<>();

    public void loadScheduleTask() {
        setupScheduleTask(RegenWorldTask.class);
    }

    public Collection<ScheduleTask> getScheduleTasks() {
        return scheduleTasks.values();
    }

    private void setupScheduleTask(Class<? extends ScheduleTask> task) {
        Set<String> worlds = Reset.instance.getRefreshConfig().getInstance().keySet();

        if (worlds == null){
            Logger.warn("任务无内容，已跳过加载");
            return;
        }

        try {

            for (String world : worlds) {
                ScheduleTask scheduleTask = task.getDeclaredConstructor(String.class).newInstance(world);
                scheduleTask.load();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearTasks() {
        scheduleTasks.clear();
    }

    public void addTask(String name, ScheduleTask scheduleTask) {
        scheduleTasks.put(name, scheduleTask);
    }

    public ScheduleTask getTask(String name) {
        return scheduleTasks.get(name);
    }
}
