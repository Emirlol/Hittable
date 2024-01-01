plugins {
	id("fabric-loom") version "1.4-SNAPSHOT"
	id("maven-publish")
	kotlin("jvm") version "1.9.22"
}

group = "me.lumiafk"
version = 1.1

repositories {
	mavenCentral()
	maven("https://api.modrinth.com/maven")
	maven("https://maven.terraformersmc.com/")
}

dependencies {
	minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
	mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
	modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${properties["fabric_version"]}")
	modCompileOnly("com.terraformersmc:modmenu:8.0.1")
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
		inputs.property("version", project.version)
		filteringCharset = "UTF-8"
		filesMatching("fabric.mod.json") {
			expand("version" to project.version)
		}
	}
	compileJava {
		options.encoding = "UTF-8"
	}
	compileKotlin {
		kotlinOptions.jvmTarget = "17"
	}
	jar {
		from("LICENSE") {
			rename { "${this}_Hittable" }
		}
		archiveBaseName.set("Hittable.jar")
	}
}
