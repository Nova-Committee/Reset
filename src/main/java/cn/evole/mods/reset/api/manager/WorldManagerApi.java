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
    private final MinecraftServer server;

    public WorldManagerApi(MinecraftServer server){
        this.server = server;
    }
    public ServerLevel createWorld(String id, ResourceKey<DimensionType> dim, ChunkGenerator gen, Difficulty dif, long seed) {
        RuntimeWorldConfig config = new RuntimeWorldConfig()
                .setDimensionType(dim)
                .setGenerator(gen)
                .setDifficulty(Difficulty.NORMAL)
                .setSeed(seed)
                ;

        Fantasy fantasy = Fantasy.get(this.server);
        RuntimeWorldHandle worldHandle = fantasy.getOrOpenPersistentWorld(new ResourceLocation(id), config);
        return worldHandle.asWorld();
    }

    public void deleteWorld(String id) {
        Fantasy fantasy = Fantasy.get(this.server);
        RuntimeWorldHandle worldHandle = fantasy.getOrOpenPersistentWorld(new ResourceLocation(id), null);
        worldHandle.delete();
    }
    public ServerLevel world(String id) {
        Fantasy fantasy = Fantasy.get(this.server);
        RuntimeWorldHandle worldHandle = fantasy.getOrOpenPersistentWorld(new ResourceLocation(id), null);
        return worldHandle.asWorld();
    }
    public long worldSeed(String id) {
        Fantasy fantasy = Fantasy.get(this.server);
        RuntimeWorldHandle worldHandle = fantasy.getOrOpenPersistentWorld(new ResourceLocation(id), null);
        return worldHandle.asWorld().getSeed();
    }

    public boolean resetWorld(String id, ResourceKey<DimensionType> dim, ChunkGenerator gen, Difficulty dif, long seed) {
        try {
            deleteWorld(id);
            createWorld(id, dim, gen, dif, seed);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
