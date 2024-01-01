# Configuring
Modmenu is pretty much necessary for this. The color is saved as a single integer under `.minecraft/config/Hittable-GlowColor.txt` in an ARGB format, so editing that by yourself won't be very easy unless you know how to construct such an integer.

You can access the config screen with modmenu, there's a single text field that accepts colors in RGB or RGBA in hexadecimal or decimal format (with each channel separated by a ',').

Examples: `#0388fc`; `03FC39AA`; `248, 3, 252`; `255,196,0,125`

Notes: 
* Alpha (transparency) is assumed to be 255 (opaque) if left empty
* Spaces are optional in decimal format
* \# is optional in hexadecimal format

# Building from source
Simply run the `assemble` task in gradle.
