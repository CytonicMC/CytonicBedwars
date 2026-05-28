rootProject.name = "CytonicBedwars"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.foxikle.dev/cytonic")
        maven("https://repo.minestom-united.dev/snapshots")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
