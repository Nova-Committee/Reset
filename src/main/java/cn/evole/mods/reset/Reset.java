package cn.evole.mods.reset;

import cn.evole.config.bukkit.serialization.ConfigurationSerialization;
import cn.evole.mods.reset.api.manager.WorldManagerApi;
import cn.evole.mods.reset.config.RefreshConfig;
import cn.evole.mods.reset.config.WorldRefresh;
import cn.evole.mods.reset.task.RegenWorldTask;
import com.github.houbb.sandglass.core.util.SandGlassHelper;
import lombok.val;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Author cnlimiter
 * CreateTime 2023/6/7 22:15
 * Name Reset
 * Description
 */

public class Reset implements ModInitializer {
    private final Logger log = LogManager.getLogger("Reset");
    public static Reset instance = new Reset();

    @Override
    public void onInitialize() {
        val refreshConfig = new RefreshConfig(FabricLoader.getInstance().getConfigDir().toFile().getAbsolutePath());
        ConfigurationSerialization.registerClass(WorldRefresh.class);
        refreshConfig.load();
        ServerLifecycleEvents.SERVER_STARTED.register(server1 -> {
            log.info(refreshConfig.getInstance().keySet());
            val worldManager = new WorldManagerApi(server1);
            Set<String> worlds = refreshConfig.getInstance().keySet();
            for (String name : worlds){
                System.out.println(name);
                RegenWorldTask task = new RegenWorldTask(name, refreshConfig, worldManager, server1);
                SandGlassHelper.schedule(task, task.getTrigger());
            }
        });
    }
    public Logger getLog() {
        return log;
    }

}
