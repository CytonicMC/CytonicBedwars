plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "9.4.2"
    id("io.freefair.lombok") version "9.5.0"
    id("io.ebean") version "17.6.0"
    id("net.cytonic.migration-generator") version "1.0-SNAPSHOT"
    id("dev.minestomunited.minestom-events") version "0.0.1-SNAPSHOT"
}

group = "net.cytonic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.foxikle.dev/cytonic")
    mavenLocal()
}

dependencies {
    implementation("net.cytonic:Cytosis:${findProperty("cytosis-version")}")
    implementation(libs.schem)
}

migration {
    id = "cytonicbedwars"
    platform = io.ebean.annotation.Platform.POSTGRES
}

minestomEvents {
    outputPackage = "net.cytonic.cytonicbedwars.utils"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.named<JavaExec>("run") {
    workingDir = file("run")
}

tasks {
    application {
        mainClass.set("net.cytonic.cytonicbedwars.BedwarsMain")
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