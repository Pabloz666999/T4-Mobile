pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    // TAMBAHKAN BLOK INI:
    plugins {
        id("com.android.application") version "9.2.0" // Sesuaikan dengan versi Android Studio kamu
        id("org.jetbrains.kotlin.android") version "2.0.21" // Versi stabil
        id("com.android.library") version "9.2.0"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "studentcontactapp"
include(":app")