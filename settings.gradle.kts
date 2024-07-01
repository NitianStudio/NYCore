pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven { url = uri("https://maven.neoforged.net/releases") }
    }
}

plugins {
    id("net.neoforged.gradle.platform") version("7.0.104")
}

rootProject.name = "neo-youth-core"

