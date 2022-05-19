/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("care.data4life.integration.java.MainKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation("care.data4life.hc-sdk-kmp:sdk-jvm:1.16.0")
}

