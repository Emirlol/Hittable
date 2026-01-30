# Changelog

## v2.3.1
Added 1.21.11 support.

This is purely a metadata change, the mod is still functional in 1.21.11 without any changes.

## v2.3.0
- Updated to support Minecraft versions 1.21.9 and 1.21.10
    - Due to the rendering changes that have happened, this required a rewrite of the mod. It wasn't a big change since the core of the mod is 2 lines of code (before and now still), but still, it might have introduced other issues that I am not aware of. Please let me know if you encounter any issues while using the mod.


## v2.2.4
Added 1.21.7 & 1.21.8 support.

This is purely a metadata change, as the mod is still functional in 1.21.7 and 1.21.8 without any changes.

## v2.2.3
Added 1.21.6 support.

This is purely a metadata change, as the mod is still functional in 1.21.6 without any changes.

## v2.2.2
- Added support for Minecraft 1.21.5

The only difference between this and the previous version is the metadata to allow for usage in 1.21.5.

## v2.2.1
Added support for Minecraft 1.21.4

## v2.2.0
- YACL (Yet Another Config Lib) is no longer embedded in the mod jar. You have to download it separately to continue using the mod. You can download it from [here](https://modrinth.com/mod/yacl/versions?g=1.21.1&l=fabric).
- Fix outlines not rendering on entities other than the targeted entity.

## v2.1.1
Updated for minecraft version 1.21

Also updated some dependencies

## v2.1
- Added a command to open the config screen: `/hittable config`
- Made the `enabled` option actually functional
- Simplified logic. This might be fixing some unintentional glow color changes.

## v2.0
Updated to 1.20.5 & 1.20.6

The mod now uses YACL for config, which is JiJ'ed so you don't have to download it separately. Modmenu is still required to open the config screen as a result of skill issue on my part.

## v1.1
Fixed the default color having an alpha value of 0, thereby not glowing.

## v1.0
Initial release. I haven't tested the mod in all 1.20 versions - only tried out 1.20.2 and 1.20.4, so don't be surprised if something breaks.