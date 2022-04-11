/*
 * Copyright (c) 2021 D4L data4life gGmbH - All rights reserved.
 */

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object Repository {
    private const val gitHubOrgD4l = "d4l-data4life"

    val d4l = listOf(
        // Maven dependency group
        "care.data4life.hc-sdk-kmp",
        "care.data4life.hc-util-sdk-kmp",
        "care.data4life.hc-securestore-kmp"
    )
}

fun RepositoryHandler.d4l() {
    maven("https://raw.github.com/d4l-data4life/maven-repository/main/releases") {
        content {
            Repository.d4l.forEach { group ->
                includeGroup(group)
            }
        }
    }
    maven("https://raw.github.com/d4l-data4life/maven-repository/main/snapshots") {
        content {
            Repository.d4l.forEach { group ->
                includeGroup(group)
            }
        }
    }
    maven("https://raw.github.com/d4l-data4life/maven-repository/main/features") {
        content {
            Repository.d4l.forEach { group ->
                includeGroup(group)
            }
        }
    }
}
