package me.ancientri.hittable

import com.mojang.brigadier.Command
import me.ancientri.hittable.config.ConfigHandler
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

object Hittable {
    @Suppress("unused")
    fun onInitializeClient() {
        check(ConfigHandler.load()) { "Failed to load config." }
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal(NAMESPACE).then(literal("config").executes { context ->
                    context.source.client.let {
                        it.send {
                            it.setScreen(ConfigHandler.createGui(it.currentScreen))
                        }
                    }
                    Command.SINGLE_SUCCESS
                })
            )
        }
    }

    const val NAMESPACE = "hittable"
}