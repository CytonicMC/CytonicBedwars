plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenLocal()
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.foxikle.dev/public")
    maven("https://jitpack.io/")
    maven("https://repo.infernalsuite.com/repository/maven-snapshots/")
    maven("https://repo.rapture.pw/repository/maven-releases/")
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    api("net.wesjd:anvilgui:1.9.0-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    compileOnly ("me.clip:placeholderapi:2.11.5")
    implementation("fr.mrmicky:fastboard:2.0.2")
    compileOnly("dev.foxikle:customnpcs:1.6-pre3")
    implementation("com.github.coderFlameyosFlow:WoodyMenus:1.5.7")
    compileOnly("com.infernalsuite.aswm:api:1.20.2-R0.1-SNAPSHOT")
}

group = "dev.foxikle"
version = "0.01-alpha2"
description = "WebNet's bedwars plugin"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks {
    assemble {
        dependsOn(reobfJar)
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
        val props = mapOf(
                "name" to project.name,
                "version" to project.version,
                "description" to project.description,
                "apiVersion" to "1.20"
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    reobfJar {
        outputJar.set(layout.buildDirectory.file(providers.gradleProperty("plugin_dir").get() + "/WebNetBedwars-${project.version}.jar"))
    }

    shadowJar {
        relocate("org.bstats", "dev.foxikle.dependencies.bstats")
        relocate("fr.mrmicky.fastboard", "dev.foxikle.fastboard")
    }
}
