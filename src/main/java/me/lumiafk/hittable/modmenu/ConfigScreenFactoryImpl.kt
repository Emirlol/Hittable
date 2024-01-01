package me.lumiafk.hittable.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import net.minecraft.client.gui.screen.Screen

class ConfigScreenFactoryImpl: ConfigScreenFactory<ConfigScreen> {
	override fun create(parent: Screen): ConfigScreen {
		return ConfigScreen(parent)
	}
}