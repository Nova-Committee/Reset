package cn.evole.mods.reset.api.teleport;

import com.google.common.base.Preconditions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:22
 * Name TeleportApi
 * Description
 */

public class TeleportApi {
    private static PortalInfo currentTarget;

    private TeleportApi() {
        throw new AssertionError();
    }

    public static PortalInfo getCustomTarget() {
        return currentTarget;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Entity> E changeDimension(E teleported, ServerLevel dimension, PortalInfo target) {
        Preconditions.checkArgument(!teleported.level.isClientSide, "Entities can only be teleported on the server side");
        Preconditions.checkArgument(Thread.currentThread() == ((ServerLevel) teleported.level).getServer().getRunningThread(), "Entities must be teleported from the main server thread");

        try {
            currentTarget = target;
            return (E) teleported.changeDimension(dimension);
        } finally {
            currentTarget = null;
        }
    }
}
