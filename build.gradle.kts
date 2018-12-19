buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.kotlin)
    }
}

plugins {
    // https://github.com/ben-manes/gradle-versions-plugin
    id("com.github.ben-manes.versions") version "0.20.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "5.0"
    distributionType = Wrapper.DistributionType.ALL
}
