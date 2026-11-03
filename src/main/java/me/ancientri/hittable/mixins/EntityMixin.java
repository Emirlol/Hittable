package me.ancientri.hittable.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.ancientri.hittable.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class EntityMixin {
	@WrapMethod(method = "getTeamColor")
	private int hittable$getTeamColorValue(Operation<Integer> original) {
		//noinspection ConstantValue
		return (Minecraft.getInstance().crosshairPickEntity == (Object) this) && ConfigHandler.INSTANCE.getConfig().getEnabled()
			   ? ConfigHandler.INSTANCE.getConfig().getColor().getRGB()
			   : original.call();
	}
}
