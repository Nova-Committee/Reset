package cn.evole.mods.reset.config;

import cn.evole.config.YmlConfig;
import cn.evole.config.api.ConfigField;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:50
 * Name ResetConfig
 * Description
 */
@Getter
public class RefreshConfig extends YmlConfig {
    public RefreshConfig(File folder) {
        super(folder.getAbsolutePath() + "refresh.yml");
    }

    @ConfigField("自动刷新世界")
    private Map<String, WorldRefresh> worldRefreshes = new HashMap<>();

    public void add(String path, WorldRefresh refresh){
        this.worldRefreshes.put(path, refresh);
    }

    public WorldRefresh get(String path){
        return this.worldRefreshes.get(path);
    }
}
