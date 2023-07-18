import com.github.gradle.node.yarn.task.YarnTask

plugins {
    id("java")
    id("com.github.node-gradle.node") version "5.0.0" // NodeJS support
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.13.3"
}

group = "co.anbora.labs"
version = "1.0.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2.5")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

// Set the Nodejs language
node {
    version.set("18.16.0")
    distBaseUrl.set("https://nodejs.org/dist")
    download.set(true)
    yarnWorkDir.set(file("${project.projectDir}/.cache/yarn"))
    nodeProjectDir.set(file("${project.projectDir}/src/main/resources/webview"))
}

tasks.register<YarnTask>("buildYarn") {
    dependsOn(tasks.yarn)
    yarnCommand.set(listOf("run", "build"))
    inputs.dir("src/")
}

tasks.register<Delete>("cleanYarnModules") {
    delete("${project.projectDir}/src/main/resources/webview/node_modules")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    yarn {
        nodeModulesOutputFilter {
            exclude("notExistingFile")
        }
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
