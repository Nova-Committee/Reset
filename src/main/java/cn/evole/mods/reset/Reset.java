package cn.evole.mods.reset;

import cn.evole.config.bukkit.serialization.ConfigurationSerialization;
import cn.evole.mods.reset.api.manager.WorldManagerApi;
import cn.evole.mods.reset.config.RefreshConfig;
import cn.evole.mods.reset.config.model.WorldRefresh;
import cn.evole.mods.reset.task.TaskManager;
import cn.evole.mods.reset.task.TaskRunnable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Author cnlimiter
 * CreateTime 2023/6/7 22:15
 * Name Reset
 * Description
 */

public class Reset implements ModInitializer {
    private RefreshConfig refreshConfig;
    private MinecraftServer server;
    private final Logger log = LogManager.getLogger("Reset");
    private WorldManagerApi worldManager;
    private final TaskManager taskManager = new TaskManager();

    public static Reset instance = new Reset();

    @Override
    public void onInitialize() {
        this.refreshConfig = new RefreshConfig(FabricLoader.getInstance().getConfigDir().toFile());
        ConfigurationSerialization.registerClass(WorldRefresh.class);
        this.refreshConfig.load();
        log.info(refreshConfig.getInstance().keySet());
//        ServerLifecycleEvents.SERVER_STARTING.register(server1 -> {
//                    this.server = server1;
//                    this.worldManager = new WorldManagerApi(server1);
//                }
//        );
//        ServerLifecycleEvents.SERVER_STARTED.register(server1 -> TaskRunnable.load());
    }

    public RefreshConfig getRefreshConfig() {
        return refreshConfig;
    }

    public MinecraftServer getServer() {
        return server;
    }
    public Logger getLog() {
        return log;
    }
    public WorldManagerApi getWorldManager() {
        return worldManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public ServerLevel getServerWorld(String id) {
        ResourceKey<Level> worldKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(id));
        ServerLevel world = this.server.getLevel(worldKey);
        if (world == null) {
            throw new IllegalStateException(String.format("World '%s' not found!", worldKey.location()));
        }
        return world;
    }
}
