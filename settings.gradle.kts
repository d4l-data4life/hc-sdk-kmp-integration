/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

includeBuild("gradlePlugin/integration-dependency")

include(":app-android")
include(":app-java")


rootProject.name = "hc-sdk-kmp-integration"
