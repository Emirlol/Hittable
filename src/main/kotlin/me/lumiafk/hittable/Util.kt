package me.lumiafk.hittable

import net.minecraft.text.Text

object Util {
    val String.text: Text get() = Text.of(this)
}