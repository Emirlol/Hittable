plugins {
	alias(libs.plugins.loom)
	alias(libs.plugins.kotlin)
	alias(libs.plugins.modPublish)
}

group = "me.lumiafk"
version = "${properties["mod_version"]}+${libs.versions.minecraft.get()}"

repositories {
	mavenCentral()
	maven("https://api.modrinth.com/maven")
	maven("https://maven.terraformersmc.com/")
	maven("https://maven.isxander.dev/releases")
}

dependencies {
	minecraft(libs.minecraft)
	mappings(variantOf(libs.yarn) { classifier("v2") })
	modImplementation(libs.fabricLoader)

	modImplementation(libs.fabricApi)
	modImplementation(libs.fabricLanguageKotlin)
	modImplementation(libs.yacl)
	modCompileOnly(libs.modMenu)
}

tasks {
	processResources {
		val map = mapOf(
			"version" to version,
			"minecraft_version" to libs.versions.minecraft.get(),
			"fabric_language_kotlin_version" to libs.versions.fabricLanguageKotlin.get(),
			"yacl_version" to libs.versions.yacl.get(),
			"modmenu_version" to libs.versions.modMenu.get(),
		)
		filteringCharset = "UTF-8"
		inputs.properties(map)
		filesMatching("fabric.mod.json") {
			expand(map)
		}
	}
	kotlin {
		jvmToolchain(21)
	}
	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesName.get()}" }
		}
	}
}

publishMods {
	file = tasks.remapJar.get().archiveFile
	modLoaders.add("fabric")
	type = STABLE
	displayName = "Hittable ${properties["mod_version"]} for Minecraft ${libs.versions.minecraft.get()}"
	changelog = """
		
	""".trimIndent()
	modrinth {
		accessToken = providers.environmentVariable("MODRINTH_TOKEN")
		projectId = "NxDKOEV1"
		minecraftVersions.addAll("1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.6")
		requires("fabric-api")
		requires("fabric-language-kotlin")
		requires("yacl")
		optional("modmenu")
		featured = true
	}
}