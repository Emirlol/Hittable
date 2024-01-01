package me.lumiafk.hittable.config

import me.lumiafk.hittable.modmenu.ConfigScreen.Companion.hexToInt
import net.minecraft.client.MinecraftClient
import net.minecraft.util.math.ColorHelper

object Config {
	var color: Int = read()
		set(color1) {
			red = ColorHelper.Argb.getRed(color1)
			green = ColorHelper.Argb.getGreen(color1)
			blue = ColorHelper.Argb.getBlue(color1)
			alpha = ColorHelper.Argb.getAlpha(color1)
			field = color1
		}
	var red = ColorHelper.Argb.getRed(color)
	var green = ColorHelper.Argb.getGreen(color)
	var blue = ColorHelper.Argb.getBlue(color)
	var alpha = ColorHelper.Argb.getAlpha(color)

	fun write(color: Int) {
		MinecraftClient.getInstance()?.runDirectory?.resolve("config/Hittable-GlowColor.txt")?.let { file ->
			file.parentFile.mkdirs()
			if (!file.exists()) file.createNewFile()
			file.writeText("$color")
			this.color = color
		}
	}

	fun read(): Int {
		MinecraftClient.getInstance()?.runDirectory?.resolve("config/Hittable-GlowColor.txt")?.let { file ->
			if (file.exists()) return file.readText().toInt()
		}
		return "FFFFFFFF".hexToInt()
	}

	fun reload() {
		color = read()
	}
}