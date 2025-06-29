plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta17"
    id("io.freefair.lombok") version "8.14"
}

group = "net.cyonic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.foxikle.dev/cytonic")
    maven("https://jitpack.io")
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
    jar {
        archiveFileName.set("CytonicBedwars.jar")
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