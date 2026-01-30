package me.ancientri.hittable.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.ancientri.hittable.config.ConfigHandler

class ModMenuApiImpl : ModMenuApi {
	override fun getModConfigScreenFactory() = ConfigScreenFactory(ConfigHandler::createGui)
}