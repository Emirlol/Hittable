plugins {
	id("fabric-loom") version "1.6-SNAPSHOT"
	id("maven-publish")
	kotlin("jvm") version "1.9.24"
}

group = "me.lumiafk"
version = 2.1

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
	include(modImplementation("dev.isxander:yet-another-config-lib:${project.property("yacl_version")}")!!)
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}
}

tasks {
	processResources {
		filteringCharset = "UTF-8"
		filesMatching("fabric.mod.json") {
			expand(
				"version" to project.version,
				"fabric_kotlin_version" to project.properties["fabric_kotlin_version"]
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