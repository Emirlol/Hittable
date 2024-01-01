package me.lumiafk.hittable.modmenu

import me.lumiafk.hittable.config.Config
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.option.GameOptionsScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.screen.ScreenTexts
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper

class ConfigScreen(private val parentScreen: Screen): GameOptionsScreen(parentScreen, MinecraftClient.getInstance().options, Text.of("Hittable Config")) {

	private lateinit var colorPicker: TextFieldWidget
	private lateinit var saveAndQuitButton: ButtonWidget
	private val colorRegex = Regex("#?(?<RGBHex>[0-9a-fA-F]{6})|#?(?<RGBAHex>[0-9a-fA-F]{8})|(?<RGBDecimal>(?:[0-9]{1,3},){2}[0-9]{1,3})|(?<RGBADecimal>(?:[0-9]{1,3},){3}[0-9]{1,3})")

	override fun init() {
		colorPicker = TextFieldWidget(this.textRenderer, this.width - 300, 30, 260, 20, Text.of("Enter a color")).apply {
			setChangedListener { color ->
				update(color)
			}
			setMaxLength(20)
		}
		addSelectableChild(colorPicker)
		setInitialFocus(colorPicker)
		saveAndQuitButton = addDrawableChild(ButtonWidget.builder(Text.of("Save & Quit")) { saveAndQuit() }.dimensions(this.width / 2 + 20, this.height - 40, 180, 20).build())
		addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL) { close() }.dimensions(this.width / 2 - 200, this.height - 40, 180, 20).build());
		if (colorPicker.text.isEmpty()) colorPicker.setText("${Config.red}, ${Config.green}, ${Config.blue}, ${Config.alpha}")
		update(colorPicker.text)
	}

	override fun resize(client: MinecraftClient, width: Int, height: Int) {
		val string = colorPicker.text
		init(client, width, height)
		colorPicker.setText(string)
	}

	override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
		super.render(context, mouseX, mouseY, delta)
		context?.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 0xFFFFFF)
		context?.drawText(this.textRenderer, "Enter a color", 40, 36, 0xFFFFFF, false)
		if (mouseX in 40..this.width - 330 && mouseY in 30..50) context?.drawTooltip(this.textRenderer, listOf("Enter an RGB or RGBA color in hexadecimal or decimal format (e.g. #FF0000; FF0000; 255,0,0; 255, 0, 0, 255).", "Alpha is assumed to be 255 (fully opaque) if left empty.").map { Text.literal(it) }, mouseX, mouseY)
		colorPicker.render(context, mouseX, mouseY, delta);
		renderSelectedColor(context, colorPicker.text)
	}

	private fun renderSelectedColor(context: DrawContext?, color: String) {
		val formattedColor = if (isValidColor(color)) formatColor(color) else 0
		context?.fill(this.width - 325, 30, this.width - 305, 50, formattedColor)
	}

	override fun renderBackground(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
		this.renderBackgroundTexture(context)
	}

	private fun update(color: String) {
		isValidColor(color).let { valid ->
			saveAndQuitButton.active = valid
			colorPicker.setEditableColor(if (valid) 0xFFFFFF else 0xFF0000)
		}
	}

	private fun saveAndQuit() {
		Config.write(formatColor(colorPicker.text))
		close()
	}

	override fun close() {
		client?.setScreen(parentScreen)
		Config.reload()
	}

	// RGB or RGBA in hex or decimal
	private fun isValidColor(color: String) = color.removeSpaces().matches(colorRegex)

	private fun formatColor(color: String): Int {
		colorRegex.matchEntire(color.removeSpaces())?.let { match ->
			(match.groups as? MatchNamedGroupCollection ?: return 0).let { groups ->
				groups["RGBHex"]?.let { match ->
					return match.value.replace("#","").hexToInt()
				}
				groups["RGBAHex"]?.let { match ->
					return match.value.replace("#","").hexToInt()
				}
				groups["RGBDecimal"]?.let { match ->
					return match.value.split(",").map { it.toInt() }.let { ColorHelper.Argb.getArgb(255, it[0], it[1], it[2]) }
				}
				groups["RGBADecimal"]?.let { match ->
					return match.value.split(",").map { it.toInt() }.let { ColorHelper.Argb.getArgb(it[3], it[0], it[1], it[2]) }
				}
			}
		}
		return 0 //Not necessary since this function will only be called if the color is valid, but the compiler doesn't know that
	}

	private fun String.hexToInt(): Int {
		val r = this.substring(0, 2).toInt(16)
		val g = this.substring(2, 4).toInt(16)
		val b = this.substring(4, 6).toInt(16)
		val a = if (this.length == 8) this.substring(6, 8).toInt(16) else 255
		return ColorHelper.Argb.getArgb(a, r, g, b)
	}

	private fun String.removeSpaces() = this.replace(" ", "")
}
