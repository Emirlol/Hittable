plugins {
	id("fabric-loom") version "1.8-SNAPSHOT"
	kotlin("jvm") version "2.0.0"
	id("me.modmuss50.mod-publish-plugin") version "0.5.1"
}

group = "me.lumiafk"
version = "2.1.1"

repositories {
	mavenCentral()
	maven("https://api.modrinth.com/maven")
	maven("https://maven.terraformersmc.com/")
	maven("https://maven.isxander.dev/releases")
}

dependencies {
	minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
	mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
	modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${properties["fabric_version"]}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${properties["fabric_kotlin_version"]}")
	modCompileOnly("com.terraformersmc:modmenu:${properties["modmenu_version"]}")
	modImplementation("dev.isxander:yet-another-config-lib:${properties["yacl_version"]}")
}

tasks {
	processResources {
		filteringCharset = "UTF-8"
		filesMatching("fabric.mod.json") {
			expand(
				"version" to version,
				"fabric_kotlin_version" to project.properties["fabric_kotlin_version"],
				"yacl_version" to project.properties["yacl_version"]
			)
		}
	}
	kotlin {
		jvmToolchain(21)
	}
	jar {
		from("LICENSE") {
			rename { "${this}_Hittable" }
		}
	}
}

publishMods {
	file = tasks.remapJar.get().archiveFile
	modLoaders.add("fabric")
	type = STABLE
	displayName = "Hittable ${version.get()}"
	changelog = ""
	modrinth {
		accessToken = providers.environmentVariable("MODRINTH_TOKEN")
		projectId = "NxDKOEV1"
		minecraftVersions.addAll("1.21")
		requires("fabric-api")
		requires("fabric-language-kotlin")
		requires("yacl")
		optional("modmenu")
	}
}