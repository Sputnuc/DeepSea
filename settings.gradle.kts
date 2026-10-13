pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/releases/")
        maven("https://raw.githubusercontent.com/GglLfr/EntityAnnoMaven/main")
        maven("https://raw.githubusercontent.com/GglLfr/MindustryClientMaven/main")

        // Ключевое добавление — Ivy-репозиторий для Mindustry
        ivy {
            url = uri("https://github.com")
            patternLayout {
                // Для конкретной версии (v160, v160.5 и т.д.)
                artifact("Anuken/Mindustry/releases/download/[revision]/dependencies.jar")
            }
            metadataSources { artifact() }
            content {
                includeVersion("Anuken", "Mindustry", "v160.5")
                includeVersion("Anuken", "Mindustry", "v160")
            }
        }
    }
    plugins {
        val entVersion = providers.gradleProperty("entVersion")
        val clientVersion = providers.gradleProperty("clientVersion")
        id("com.github.GglLfr.EntityAnno") version(entVersion)
        id("com.github.GglLfr.MindustryClient") version(clientVersion)
    }
}