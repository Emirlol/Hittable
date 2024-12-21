package me.lumiafk.hittable.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.lumiafk.hittable.config.ConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.awt.*;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Final
	@Shadow
	private MinecraftClient client;

	@WrapOperation(method = {"renderEntities", "getEntitiesToRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;hasOutline(Lnet/minecraft/entity/Entity;)Z"))
	private boolean hittable$hasOutline(MinecraftClient instance, Entity entity, Operation<Boolean> original) {
		return (client.targetedEntity == entity && ConfigHandler.INSTANCE.getConfig().getEnabled()) || original.call(instance, entity);
	}

	@WrapOperation(method = "renderEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OutlineVertexConsumerProvider;setColor(IIII)V"))
	private void hittable$setOutlineColor(OutlineVertexConsumerProvider instance, int red, int green, int blue, int alpha, Operation<Void> original, @Local LocalRef<Entity> entity) {
		if (client.targetedEntity == entity.get() && ConfigHandler.INSTANCE.getConfig().getEnabled()) {
			Color color = ConfigHandler.INSTANCE.getConfig().getColor();
			instance.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		} else {
			original.call(instance, red, green, blue, alpha);
		}
	}
}
