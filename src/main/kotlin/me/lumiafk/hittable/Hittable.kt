package me.lumiafk.hittable

import com.mojang.brigadier.Command
import me.lumiafk.hittable.config.ConfigHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents

object Hittable {
    fun onInitializeClient() {
        check(ConfigHandler.load()) { "Failed to load config." }
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("hittable").then(literal("config").executes { context ->
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
}