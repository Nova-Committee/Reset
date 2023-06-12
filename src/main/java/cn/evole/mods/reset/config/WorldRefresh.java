package cn.evole.mods.reset.config;

import cn.evole.config.bukkit.serialization.ConfigurationSerializable;
import cn.evole.config.bukkit.serialization.SerializableAs;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 15:32
 * Name WorldRefresh
 * Description
 */
@Getter
@Setter
@SerializableAs("WorldRefresh")
public class WorldRefresh implements ConfigurationSerializable {

    private String timePreset;
    private String timeNext;
    private List<String> cmds;
    private boolean useSeed;

    public WorldRefresh(String timePreset, String timeNext, boolean useSeed, @Nullable List<String> cmds){
        this.timePreset = timePreset;
        this.timeNext = Optional.ofNullable(timeNext).isPresent() ? timeNext : "";
        this.cmds = Optional.ofNullable(cmds).isPresent() ? cmds : new ArrayList<>();
        this.useSeed = useSeed;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialize = new HashMap<>();

        serialize.put("时间设置", getTimePreset());
        serialize.put("到期时间", getTimeNext());
        serialize.put("任务执行", getCmds());
        serialize.put("更换种子", useSeed);

        return serialize;
    }

    public static WorldRefresh deserialize(Map<String, Object> map) {
        return new WorldRefresh(
                (map.get("时间设置") != null ? (String) map.get("时间设置") : "month:1, 20:00:00"),
                (map.get("到期时间") != null ? (String) map.get("到期时间") : ""),
                (map.get("更换种子") != null && (boolean) map.get("更换种子")),
                (map.get("任务执行") != null ? (List<String>) map.get("任务执行") : List.of("gamerule keepInventory true"))
        );
    }
}
