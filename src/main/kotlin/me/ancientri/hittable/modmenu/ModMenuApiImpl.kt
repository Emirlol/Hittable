package me.ancientri.hittable.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.ancientri.hittable.config.ConfigHandler
import net.minecraft.client.gui.screens.Screen

class ModMenuApiImpl : ModMenuApi {
	override fun getModConfigScreenFactory() = ConfigScreenFactory<Screen>(ConfigHandler::createGui)
}