package me.lumiafk.hittable.config

import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder
import dev.isxander.yacl3.api.controller.ColorControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import me.lumiafk.hittable.Util.text
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen
import java.awt.Color

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

    fun createGui(parent: Screen?): Screen = YetAnotherConfigLib.createBuilder()
        .title("Hittable Config".text)
        .handleCategories()
        .save(this::save)
        .build()
        .generateScreen(parent)

    private fun YetAnotherConfigLib.Builder.handleCategories(): YetAnotherConfigLib.Builder = this.categories(
        listOf(
            ConfigCategory.createBuilder()
                .name("General".text)
                .option(
                    Option.createBuilder<Boolean>()
                    .name("Enabled".text)
                    .binding(
                        defaults.enabled,
                        { config.enabled },
                        { config.enabled = it })
                    .controller { BooleanControllerBuilder.create(it) }
                    .build())
                .option(
                    Option.createBuilder<Color>()
                    .name("Color".text)
                    .binding(
                        defaults.color,
                        { config.color },
                        { config.color = it })
                    .controller { ColorControllerBuilder.create(it) } //No alpha by default
                    .build())
                .build()
        )
    )

}