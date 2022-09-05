/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.gradle.integration.dependency

import org.gradle.api.Project

private val modules = listOf(
    "kotlin-stdlib-jdk7",
    "kotlin-stdlib-jdk8",
    "kotlin-stdlib",
    "kotlin-stdlib-common",
    "kotlin-reflect"
)

// Taken from https://github.com/bitPogo/kmock/blob/main/gradlePlugin/kmock-dependency/src/main/kotlin/tech/antibytes/gradle/kmock/dependency/Insurance.kt
fun Project.ensureKotlinVersion(version: String? = null) {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name in modules) {
                useVersion(version ?: Version.kotlin)
                because("Avoid resolution conflicts")
            }
        }
    }
}
