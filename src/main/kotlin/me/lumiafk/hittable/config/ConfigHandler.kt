package me.lumiafk.hittable.config

import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder
import dev.isxander.yacl3.api.controller.ColorControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import dev.isxander.yacl3.dsl.binding
import me.lumiafk.hittable.Util.text
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen
import java.awt.Color

//TODO: Consider using the yacl dsl for the config
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

    fun createGui(parent: Screen?): Screen = YetAnotherConfigLib.createBuilder().invoke {
        title("Hittable Config".text)
        category {
            name("General".text)
            option<Boolean> {
                name("Enabled".text)
                binding(config::enabled, defaults.enabled)
                onOffController()
            }
            option<Color> {
                name("Color".text)
                binding(config::color, defaults.color)
                controller { ColorControllerBuilder.create(it) }
            }
        }
        save(::save)
    }.generateScreen(parent)

    //Syntactic sugar for creating a config screen
    private operator fun YetAnotherConfigLib.Builder.invoke(block: YetAnotherConfigLib.Builder.() -> Unit): YetAnotherConfigLib = apply(block).build()

    private fun YetAnotherConfigLib.Builder.category(block: ConfigCategory.Builder.() -> Unit) = apply { category(ConfigCategory.createBuilder().apply(block).build()) }

    private fun ConfigCategory.Builder.buttonOption(block: ButtonOption.Builder.() -> Unit) = apply { option(ButtonOption.createBuilder().apply(block).build()) }

    private fun OptionGroup.Builder.buttonOption(block: ButtonOption.Builder.() -> Unit) = apply { option(ButtonOption.createBuilder().apply(block).build()) }

    private fun ConfigCategory.Builder.labelOption(block: LabelOption.Builder.() -> Unit) = apply { option(LabelOption.createBuilder().apply(block).build()) }

    private fun OptionGroup.Builder.labelOption(block: LabelOption.Builder.() -> Unit) = apply { option(LabelOption.createBuilder().apply(block).build()) }

    private fun <T> ConfigCategory.Builder.option(block: Option.Builder<T>.() -> Unit) = apply { option(Option.createBuilder<T>().apply(block).build()) }

    private fun <T> OptionGroup.Builder.option(block: Option.Builder<T>.() -> Unit) = apply { option(Option.createBuilder<T>().apply(block).build()) }

    private fun ConfigCategory.Builder.group(block: OptionGroup.Builder.() -> Unit) = apply { group(OptionGroup.createBuilder().apply(block).build()) }

    private fun Option.Builder<Boolean>.onOffController() = controller { BooleanControllerBuilder.create(it) }
}