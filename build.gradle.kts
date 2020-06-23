buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependency.GradlePlugin.android)
        classpath(Dependency.GradlePlugin.kotlin)
    }
}

plugins {
    // https://github.com/ben-manes/gradle-versions-plugin
    id("com.github.ben-manes.versions") version "0.27.0"
}

apply(from = "d4l-client-config.gradle.kts")
apply(from = "d4l-test-config.gradle.kts")

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "6.5"
    distributionType = Wrapper.DistributionType.ALL
}
