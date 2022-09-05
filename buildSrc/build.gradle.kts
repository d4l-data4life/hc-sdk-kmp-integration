/*
 * Copyright (c) 2021 D4L data4life gGmbH - All rights reserved.
 */

import care.data4life.gradle.integration.dependency.d4l

plugins {
    `kotlin-dsl`
    id("care.data4life.gradle.integration.dependency")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    d4l()
}

dependencies {
    implementation(care.data4life.gradle.integration.dependency.GradlePlugin.kotlin)
    implementation(care.data4life.gradle.integration.dependency.GradlePlugin.android)
    implementation(care.data4life.gradle.integration.dependency.GradlePlugin.jUnit5Android)

    implementation("com.google.code.gson:gson:2.8.9")

    // dependency check
    implementation("com.github.ben-manes:gradle-versions-plugin:0.42.0")
    // download scripts
    implementation("de.undercouch:gradle-download-task:4.1.2")
    // quality.gradle.kts
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.5.1")
    implementation("com.pinterest:ktlint:0.45.2")
    // versioning.gradle.kts
    implementation("care.data4life.gradle.gitversion:gradle-git-version:0.12.4-d4l")
}
