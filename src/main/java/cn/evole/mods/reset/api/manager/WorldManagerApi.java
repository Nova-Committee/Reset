package cn.evole.mods.reset.api.manager;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import xyz.nucleoid.fantasy.Fantasy;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:28
 * Name WorldManagerApi
 * Description
 */

public class WorldManagerApi {
    private final MinecraftServer mc;

    public WorldManagerApi(MinecraftServer mc){
        this.mc = mc;
    }
    public ServerLevel create_world(String id, ResourceKey<DimensionType> dim, ChunkGenerator gen, Difficulty dif, long seed) {
        RuntimeWorldConfig config = new RuntimeWorldConfig()
                .setDimensionType(dim)
                .setGenerator(gen)
                .setDifficulty(Difficulty.NORMAL)
                .setSeed(seed)
                ;

        Fantasy fantasy = Fantasy.get(this.mc);
        RuntimeWorldHandle worldHandle = fantasy.getOrOpenPersistentWorld(new ResourceLocation(id), config);
        return worldHandle.asWorld();
    }

    public void delete_world(String id) {
        Fantasy fantasy = Fantasy.get(this.mc);
        RuntimeWorldHandle worldHandle = fantasy.getOrOpenPersistentWorld(new ResourceLocation(id), null);
        worldHandle.delete();
    }

    public ServerLevel recreate_world(String id, ResourceKey<DimensionType> dim, ChunkGenerator gen, Difficulty dif, long seed) {
        delete_world(id);
        return create_world(id, dim, gen, dif, seed);
    }
}
