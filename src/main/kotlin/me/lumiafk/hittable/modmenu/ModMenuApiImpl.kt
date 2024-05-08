package me.lumiafk.hittable.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.lumiafk.hittable.config.ConfigHandler

class ModMenuApiImpl : ModMenuApi {
	override fun getModConfigScreenFactory() = ConfigScreenFactory(ConfigHandler::createGui)
}