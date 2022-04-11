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
    implementation("com.google.code.gson:gson:2.8.7")

    // dependency check
    implementation("com.github.ben-manes:gradle-versions-plugin:0.39.0")
    // download scripts
    implementation("de.undercouch:gradle-download-task:4.1.2")
    // quality.gradle.kts
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.4.2")
    implementation("com.pinterest:ktlint:0.45.2")
    // versioning.gradle.kts
    implementation("com.palantir.gradle.gitversion:gradle-git-version:0.12.3")
}
