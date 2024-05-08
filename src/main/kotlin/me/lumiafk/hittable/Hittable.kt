package me.lumiafk.hittable

import me.lumiafk.hittable.config.ConfigHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

class Hittable : ClientModInitializer {
    override fun onInitializeClient() {
        check(ConfigHandler.load()) { "Failed to load config." }
    }
}