package me.ancientri.hittable.config

import dev.isxander.yacl3.api.controller.BooleanControllerBuilder
import dev.isxander.yacl3.api.controller.ColorControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import dev.isxander.yacl3.dsl.YetAnotherConfigLib
import dev.isxander.yacl3.dsl.binding
import me.ancientri.hittable.Hittable
import me.ancientri.hittable.Util.text
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen

object ConfigHandler {
    private val HANDLER = ConfigClassHandler.createBuilder(Config::class.java).serializer {
        GsonConfigSerializerBuilder.create(it)
            .setPath(FabricLoader.getInstance().configDir.resolve("hittable.json5"))
            .setJson5(true)
            .build()
    }.build()

    fun load() = HANDLER.load()

    fun save() = HANDLER.save()

    val config: Config get() = HANDLER.instance()

    val defaults: Config get() = HANDLER.defaults()

    fun createGui(parent: Screen?): Screen = YetAnotherConfigLib(Hittable.NAMESPACE) {
        title("Hittable Config".text)
        val main by categories.registering {
            name("General".text)
            val enabled by rootOptions.registering {
                name("Enabled".text)
                binding(config::enabled, defaults.enabled)
                controller(BooleanControllerBuilder::create)
            }
            val color by rootOptions.registering {
                name("Color".text)
                binding(config::color, defaults.color)
                // TODO: Change alpha back to false. Minecraft does not support alpha in outline colors,
                //       and this is only set true due to a YACL issue that crashes the game upon opening the color picker
                controller { ColorControllerBuilder.create(it).allowAlpha(true) }
            }
        }
        save(::save)
    }.generateScreen(parent)
}