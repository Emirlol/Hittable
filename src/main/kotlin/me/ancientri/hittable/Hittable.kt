package me.ancientri.hittable

import com.mojang.brigadier.Command
import me.ancientri.hittable.config.ConfigHandler
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

object Hittable {
    @Suppress("unused")
    fun onInitializeClient() {
        check(ConfigHandler.load()) { "Failed to load config." }
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                ClientCommands.literal(NAMESPACE).then(ClientCommands.literal("config").executes { context ->
                    context.source.client.let { client ->
                    client.execute {
                        client.setScreen(ConfigHandler.createGui(client.screen))
                    }
                }
                    Command.SINGLE_SUCCESS
                })
            )
        }
    }

    const val NAMESPACE = "hittable"
}