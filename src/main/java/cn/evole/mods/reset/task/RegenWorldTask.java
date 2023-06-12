package cn.evole.mods.reset.task;

import cn.evole.mods.reset.Reset;
import cn.evole.mods.reset.api.manager.WorldManagerApi;
import cn.evole.mods.reset.config.RefreshConfig;
import cn.evole.mods.reset.util.Logger;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.core.api.job.AbstractJob;
import com.github.houbb.sandglass.core.support.trigger.CronTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 16:58
 * Name RegenWorldTask
 * Description
 */

public class RegenWorldTask extends AbstractJob {
    private final String name;
    private final WorldManagerApi manager;
    private final RefreshConfig config;
    private final ITrigger trigger;
    private final MinecraftServer server;

    public RegenWorldTask(String name, RefreshConfig config, WorldManagerApi manager, MinecraftServer server) {
        this.name = name;
        this.config = config;
        this.manager = manager;
        this.server = server;
        this.trigger = new CronTrigger(config.get(name).getTimePreset());//todo bug
    }

    @Override
    public void execute(IJobContext context) {
        List<String> worldNames = new ArrayList<>();
        server.getAllLevels().forEach(world -> worldNames.add(world.dimension().location().toString()));
        if (!worldNames.contains(name)) {
            Reset.instance.getLog().info("世界 " + name + " 不存在, 请检查配置文件");
        } else {
            ServerLevel world = server.getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(name)));
            regenWorld(world);
        }
    }

    public ITrigger getTrigger() {
        return trigger;
    }

    public List<String> getResetCommand() {
        return config.get(name).getCmds();
    }

    public void regenWorld(ServerLevel world) {
        boolean changeSeed = config.get(name).isUseSeed();
        long seed;

        if (changeSeed) {
            seed = name.hashCode();
        } else {
            seed = world.getSeed();
        }

        Logger.info("&a━━━━━━━━━━━━━━  &e正在自动刷新 " + name + " 世界  &a━━━━━━━━━━━━━━");
        Logger.info(" ");

        boolean regenSuccess = manager.resetWorld(name, world.dimensionTypeId(), world.getChunkSource().getGenerator(), world.getDifficulty(), seed);

        if (regenSuccess) {
            runResetCommand();
        } else {
            Logger.info("&c世界重置失败, 请检查是否是主世界, 主世界无法刷新");
        }

        Logger.info(" ");
        Logger.info("&a━━━━━━━━━━━━━━  &e世界 " + name + " 自动刷新完毕  &a━━━━━━━━━━━━━━");
    }

    private void runResetCommand() {
        if (getResetCommand() == null) return;

        for (String command : getResetCommand()) {
            server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), command);
        }
    }


}
