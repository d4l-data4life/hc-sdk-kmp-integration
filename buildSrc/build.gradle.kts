/*
 * Copyright (c) 2021 D4L data4life gGmbH - All rights reserved.
 */

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://dl.bintray.com/data4life/maven")
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.6")

    // dependency check
    implementation("com.github.ben-manes:gradle-versions-plugin:0.38.0")
    // download scripts
    implementation("de.undercouch:gradle-download-task:4.1.1")
    // versioning.gradle.kts
    implementation("com.palantir.gradle.gitversion:gradle-git-version:0.12.3")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
