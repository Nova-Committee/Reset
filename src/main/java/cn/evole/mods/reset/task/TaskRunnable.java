package cn.evole.mods.reset.task;

import cn.evole.mods.reset.Reset;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 17:55
 * Name TaskRunnable
 * Description
 */

public class TaskRunnable extends TimerTask {
    private static TaskRunnable taskRunnable;

    @Override
    public void run() {
        for (ScheduleTask scheduleTask : Reset.instance.getTaskManager().getScheduleTasks()) {
            if (scheduleTask.isExpired()) {
                try {
                    scheduleTask.runTask();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    scheduleTask.taskComplete();
                }
                return;
            }
        }
    }

    public static void load() {
        Timer timer = new Timer();
        if (taskRunnable == null) {
            taskRunnable = new TaskRunnable();
        }
        timer.schedule(taskRunnable, 0, 20);
    }
}
