package me.lumiafk.hittable.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements AutoCloseable {
    @Final
    @Shadow
    MinecraftClient client;

    @Unique
    Entity previousEntity = null;

    @Inject(method = "updateCrosshairTarget", at = @At("TAIL"))
    private void setTargetGlowing(CallbackInfo ci) {
        if (client.targetedEntity == null) {
            if (previousEntity != null) {
                previousEntity.setGlowing(false);
                previousEntity = null;
            }
        } else if (client.targetedEntity != previousEntity) {
            if (previousEntity != null) previousEntity.setGlowing(false);
            client.targetedEntity.setGlowing(true);
            previousEntity = client.targetedEntity;
        }
    }
}
