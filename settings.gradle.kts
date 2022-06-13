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

val includeSdk: String by settings
if (includeSdk.toBoolean()) {
    includeBuild("../hc-sdk-kmp") {
        dependencySubstitution {
            substitute(module("care.data4life.hc-sdk-kmp:sdk-android"))
                .using(project(":sdk-android"))
        }
    }
}

val includeAuth: String by settings
if (includeAuth.toBoolean()) {
    includeBuild("../hc-auth-sdk-kmp") {
        dependencySubstitution {
            substitute(module("care.data4life.hc-auth-sdk-kmp:auth"))
                .using(project(":auth"))
            substitute(module("care.data4life.hc-auth-sdk-kmp:auth-jvm"))
                .using(project(":auth"))
            substitute(module("care.data4life.hc-auth-sdk-kmp:auth-android"))
                .using(project(":auth"))
        }
    }
}
