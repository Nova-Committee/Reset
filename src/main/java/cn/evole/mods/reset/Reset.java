package cn.evole.mods.reset;

import cn.evole.mods.reset.config.RefreshConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Author cnlimiter
 * CreateTime 2023/6/7 22:15
 * Name Reset
 * Description
 */

public class Reset implements ModInitializer {
    private RefreshConfig refreshConfig;

    public static Reset instance = new Reset();
    @Override
    public void onInitialize() {
        this.refreshConfig = new RefreshConfig(FabricLoader.getInstance().getConfigDir().toFile());
        this.refreshConfig.load();
    }

    public RefreshConfig getRefreshConfig() {
        return refreshConfig;
    }

}
