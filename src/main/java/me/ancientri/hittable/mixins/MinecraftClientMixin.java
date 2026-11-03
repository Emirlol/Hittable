package me.ancientri.hittable.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.ancientri.hittable.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
	@Shadow
	@Nullable
	public Entity targetedEntity;

	@WrapMethod(method = "shouldEntityAppearGlowing")
	private boolean hittable$hasOutline(Entity entity, Operation<Boolean> original) {
		return (targetedEntity == entity && ConfigHandler.INSTANCE.getConfig().getEnabled())
				|| original.call(entity);
	}
}
