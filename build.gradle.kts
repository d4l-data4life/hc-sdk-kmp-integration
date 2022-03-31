buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.kotlin)
    }
}

plugins {
    id("scripts.dependency-updates")
    id("scripts.download-scripts")
    id("scripts.quality-spotless")
    id("scripts.versioning")
}

val gitHubUser = project.findProperty("gpr.user") as String? ?: System.getenv("PACKAGE_REGISTRY_USERNAME")
val gitHubToken = project.findProperty("gpr.key") as String? ?: System.getenv("PACKAGE_REGISTRY_TOKEN")

allprojects {
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/d4l-data4life/hc-sdk-kmp")
            credentials.username = project.findProperty("gpr.user") as String? ?: System.getenv("PACKAGE_REGISTRY_USERNAME")
            credentials.password = project.findProperty("gpr.key") as String? ?: System.getenv("PACKAGE_REGISTRY_TOKEN")
        }

//        d4l()

        jitPack()
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.4.1"
    distributionType = Wrapper.DistributionType.ALL
}
