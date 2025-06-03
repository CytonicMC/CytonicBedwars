plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta15"
    id("io.freefair.lombok") version "8.13.1"
}

group = "net.cyonic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.foxikle.dev/cytonic")
}

dependencies {
    compileOnly("net.cytonic:Cytosis:1.0-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveFileName.set("CytonicBedwars.jar")
        archiveClassifier.set("")
        mergeServiceFiles()
        if (providers.gradleProperty("server_dir").isPresent) {
            destinationDirectory.set(file(providers.gradleProperty("server_dir").get() + "/plugins"))
        }
    }
}