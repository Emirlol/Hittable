package me.lumiafk.hittable.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

class ModMenuApiImpl : ModMenuApi {
	override fun getModConfigScreenFactory(): ConfigScreenFactory<ConfigScreen> {
		return ConfigScreenFactoryImpl()
	}
}