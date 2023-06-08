package cn.evole.mods.reset.task.mode;

import cn.evole.mods.reset.Reset;
import cn.evole.mods.reset.config.WorldRefresh;
import lombok.val;

import java.time.LocalDateTime;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:44
 * Name TimeMode
 * Description
 */

public class TimeMode {
    public interface Preservable {

        default void saveNextTime(String path, String time) {
            val refresh = Reset.instance.getRefreshConfig().get(path);
            refresh.setTimeNext(time);
            Reset.instance.getRefreshConfig().add(path , refresh);
            Reset.instance.getRefreshConfig().save();
        }

        LocalDateTime getNextTime();
    }
}
