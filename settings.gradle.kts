pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        gradlePluginPortal()
    }
}
rootProject.name = "Hittable"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
