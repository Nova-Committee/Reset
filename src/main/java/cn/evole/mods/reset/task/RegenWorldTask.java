package cn.evole.mods.reset.task;

import cn.evole.mods.reset.Reset;
import cn.evole.mods.reset.api.manager.WorldManagerApi;
import cn.evole.mods.reset.config.model.WorldRefresh;
import cn.evole.mods.reset.util.Logger;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.List;
import java.util.Random;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 16:58
 * Name RegenWorldTask
 * Description
 */

public class RegenWorldTask extends ScheduleTask{
    private final ServerLevel world;
    private final WorldManagerApi manager;

    public RegenWorldTask(String name) {
        super(name);
        this.config = Reset.instance.getRefreshConfig();
        this.manager = Reset.instance.getWorldManager();
        this.world = manager.world(name);

    }

    @Override
    public void runTask() {

        if (world == null) {
            Reset.instance.getLog().info("世界 " + name + " 不存在, 请检查配置文件");
        } else {
            regenWorld();
        }

    }

    public List<String> getResetCommand() {
        return config.get(name).getCmds();
    }

    public void regenWorld() {
        boolean changeSeed = ((WorldRefresh)config.get(name)).isUseSeed();
        long seed;

        ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
        Difficulty difficulty = world.getDifficulty();
        Random rand = new Random();

        if (changeSeed) {
            seed = rand.nextLong();
        } else {
            seed = world.getSeed();
        }

        Logger.info("&a━━━━━━━━━━━━━━  &e正在自动刷新 " + name + " 世界  &a━━━━━━━━━━━━━━");
        Logger.info(" ");

        boolean regenSuccess = manager.resetWorld(name, world.dimensionTypeId(), chunkGenerator, difficulty, seed);

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
            Reset.instance.getServer().getCommands().performPrefixedCommand(Reset.instance.getServer().createCommandSourceStack(), name);
        }
    }
}
