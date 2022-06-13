import care.data4life.gradle.integration.dependency.d4l
import care.data4life.gradle.integration.dependency.ensureKotlinVersion

plugins {
    id("care.data4life.gradle.integration.dependency")

    id("scripts.dependency-updates")
    id("scripts.download-scripts")
    id("scripts.quality-spotless")
    id("scripts.versioning")
}

allprojects {
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/d4l-data4life/hc-sdk-kmp")
            credentials.username = project.findProperty("gpr.user") as String? ?: System.getenv("PACKAGE_REGISTRY_DOWNLOAD_USERNAME")
            credentials.password = project.findProperty("gpr.key") as String? ?: System.getenv("PACKAGE_REGISTRY_DOWNLOAD_TOKEN")
        }

        d4l()
    }

    // FIXME remove if dependency conflict is solved
    configurations.all {
        exclude(group = "care.data4life.hc-result-sdk-kmp", module = "error-android-debug")
        exclude(group = "care.data4life.hc-securestore-sdk-kmp", module = "securestore-android-debug")
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.4.2"
    distributionType = Wrapper.DistributionType.ALL
}
