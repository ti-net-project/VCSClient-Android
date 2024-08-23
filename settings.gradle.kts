import java.net.URI

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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
        //观测云
        maven { url = URI("https://mvnrepo.jiagouyun.com/repository/maven-releases")}

        mavenLocal()
    }
}

rootProject.name = "VCSClient-Android"
include(":sdk_aar")
include(":publish")
