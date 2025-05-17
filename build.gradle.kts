import com.github.gradle.node.yarn.task.YarnTask

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)

plugins {
    id("java")
    id("com.github.node-gradle.node") version "5.0.0" // NodeJS support
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.jetbrains.intellij.platform") version "2.6.0"
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(17)
}

// Configure project's dependencies
repositories {
    mavenCentral()

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        create(properties("platformType"), properties("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(properties("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(properties("platformPlugins").map { it.split(',') })

        instrumentationTools()
        pluginVerifier()
        // testFramework(TestFrameworkType.Platform.JUnit4)
    }
    implementation("com.google.code.gson:gson:2.10.1")
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        version = properties("pluginVersion")
        description = file("src/main/html/description.html").inputStream().readBytes().toString(Charsets.UTF_8)
        changeNotes = file("src/main/html/change-notes.html").inputStream().readBytes().toString(Charsets.UTF_8)

        ideaVersion {
            sinceBuild = properties("pluginSinceBuild")
            untilBuild = properties("pluginUntilBuild")
        }
    }

    publishing {
        token = environment("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = properties("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    verifyPlugin {
        ides {
            recommended()
        }
    }

    buildSearchableOptions = false
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
    delete("${project.projectDir}/src/main/resources/webview/.next")
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }

    withType<ProcessResources> {
        dependsOn("buildYarn", "cleanYarnModules")
    }

    yarn {
        nodeModulesOutputFilter {
            exclude("notExistingFile")
        }
    }
}
