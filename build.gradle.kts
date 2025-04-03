import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.cyonic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.foxikle.dev/cytonic")
}

dependencies {
    compileOnly("net.cytonic:Cytosis:1.0-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.36") // lombok
    annotationProcessor("org.projectlombok:lombok:1.18.36") // lombok
}

tasks {
    assemble {
        dependsOn("shadowJar")
    }
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes["Main-Class"] = "net.cytonic.cytosis.Cytosis"
        }
        mergeServiceFiles()
        archiveFileName.set("CytonicBedwars.jar")
        archiveClassifier.set("")
        if (providers.gradleProperty("server_dir").isPresent) {
            destinationDirectory.set(file(providers.gradleProperty("server_dir").get() + "/plugins"))
        }
    }
}