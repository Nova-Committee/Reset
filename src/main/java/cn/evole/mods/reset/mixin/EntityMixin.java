package cn.evole.mods.reset.mixin;

import cn.evole.mods.reset.api.teleport.TeleportApi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:20
 * Name EntityMixin
 * Description
 */

@Mixin(Entity.class)
public class EntityMixin {

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "findDimensionEntryPoint", at = @At("HEAD"), cancellable = true, allow = 1)
    public void getTeleportTarget(ServerLevel serverLevel, CallbackInfoReturnable<PortalInfo> cir) {
        Entity self = (Entity) (Object) this;
        // Check if a destination has been set for the entity currently being teleported
        PortalInfo customTarget = TeleportApi.getCustomTarget();

        if (customTarget != null) cir.setReturnValue(customTarget);
    }

}
