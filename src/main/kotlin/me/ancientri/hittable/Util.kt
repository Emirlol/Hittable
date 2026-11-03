package me.ancientri.hittable

import net.minecraft.network.chat.Component

object Util {
    val String.text: Component get() = Component.literal(this)
}
