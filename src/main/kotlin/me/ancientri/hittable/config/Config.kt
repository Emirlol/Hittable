package me.ancientri.hittable.config

import dev.isxander.yacl3.config.v2.api.SerialEntry
import java.awt.Color

class Config {
	@SerialEntry
	var enabled = true

	@SerialEntry
	var color = Color(0xFFFFFF)
}