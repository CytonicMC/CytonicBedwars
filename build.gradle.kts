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
    compileOnly("org.projectlombok:lombok:1.18.32") // lombok
    annotationProcessor("org.projectlombok:lombok:1.18.32") // lombok
}

tasks.withType<JavaCompile> {
    // use String templates
    options.compilerArgs.add("--enable-preview")
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
        archiveFileName.set("CytonicBedwars-${project.version}.jar")
        archiveClassifier.set("")
        destinationDirectory.set(File(providers.gradleProperty("server_dir")
            .orElse(destinationDirectory.get().toString()).toString() + "/plugins"))
    }
}