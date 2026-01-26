plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.1"
    id("io.freefair.lombok") version "9.2.0"
    id("net.cytonic.run-cytosis") version "1.0"
}

group = "net.cyonic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.foxikle.dev/cytonic")
}

dependencies {
    compileOnly("net.cytonic:Cytosis:${findProperty("cytosis-version")}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
    runCytosis {
        cytosisVersion = findProperty("cytosis-version") as String
    }
    shadowJar {
        archiveFileName.set("CytonicBedwars.jar")
        archiveClassifier.set("")
        mergeServiceFiles()
    }
    jar {
        enabled = false
    }
}