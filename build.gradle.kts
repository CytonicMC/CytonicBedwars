plugins {
    id("java")
    application
    alias(libs.plugins.shadow)
    alias(libs.plugins.lombok)
    alias(libs.plugins.minestomEvents)
    alias(libs.plugins.jandex)
    alias(libs.plugins.blossom)
    alias(libs.plugins.indragit)
}

group = "net.cytonic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.foxikle.dev/cytonic")
}

dependencies {
    implementation(libs.cytosis)
    implementation(libs.schem)
}

minestomEvents {
    outputPackage = "net.cytonic.cytonicbedwars.utils"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.named<JavaExec>("run") {
    workingDir = file("run")
    dependsOn("generateEvents")
    dependsOn("jandex")
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}

jandex {
    toolVersion = "3.6.0"
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("buildVersion", project.version.toString())
                property("gitCommit", indraGit.commit().get().name())
                properties.put("builtAt", System.currentTimeMillis())
            }
        }
    }
}

tasks {
    application {
        mainClass.set("net.cytonic.cytonicbedwars.BedwarsMain")
    }
    shadowJar {
        archiveFileName.set("CytonicBedwars.jar")
        dependsOn("jandex")
        archiveClassifier.set("")
        mergeServiceFiles()
    }
    jar {
        enabled = false
    }
}