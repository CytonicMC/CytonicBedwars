plugins {
    id("java")
    application
    alias(libs.plugins.shadow)
    alias(libs.plugins.lombok)
    alias(libs.plugins.minestomEvents)
    alias(libs.plugins.jandex)
    alias(libs.plugins.blossom)
    alias(libs.plugins.indragit)
    alias(libs.plugins.graalvm)
}

group = "net.cytonic"

repositories {
    mavenCentral()
    maven("https://repo.foxikle.dev/cytonic")
}

dependencies {
    implementation(libs.cytosis)
    implementation(libs.schem)
}

minestomEvents {
    outputPackage = "net.cytonic.bedwars.utils"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.named<JavaExec>("run") {
    workingDir = file("run")
    dependsOn("generateEvents")
    dependsOn("jandex")
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
        mainClass.set("net.cytonic.bedwars.BedwarsMain")
    }
    shadowJar {
        archiveFileName.set("Bedwars.jar")
        dependsOn("jandex")
        archiveClassifier.set("")
        mergeServiceFiles()
    }
    jar {
        enabled = false
    }
}