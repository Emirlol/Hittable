package me.lumiafk.hittable.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.lumiafk.hittable.config.ConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class EntityMixin {
	@WrapMethod(method = "getTeamColorValue")
	private int hittable$getTeamColorValue(Operation<Integer> original) {
		//noinspection ConstantValue
		return (MinecraftClient.getInstance().targetedEntity == (Object) this) && ConfigHandler.INSTANCE.getConfig().getEnabled()
			   ? ConfigHandler.INSTANCE.getConfig().getColor().getRGB()
			   : original.call();
	}
}
