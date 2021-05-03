/*
 * Copyright (c) 2021 D4L data4life gGmbH - All rights reserved.
 */

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object Repository {
    private const val gitHubOrgD4l = "d4l-data4life"

    val github = listOf(
        // GitHub organization, GitHub repository name, Maven dependency group
        listOf(gitHubOrgD4l, "hc-sdk-kmp", "care.data4life.hc-sdk-kmp"),
        listOf(gitHubOrgD4l, "hc-util-sdk-kmp", "care.data4life.hc-util-sdk-kmp"),
        listOf(gitHubOrgD4l, "hc-fhir-sdk-java", "care.data4life.hc-fhir-sdk-java"),
        listOf(gitHubOrgD4l, "hc-fhir-helper-sdk-kmp", "care.data4life.hc-fhir-helper-sdk-kmp")
    )

    val d4l = listOf(
        // Maven dependency group
        "care.data4life.hc-sdk-kmp",
        "care.data4life.hc-util-sdk-kmp",
        "care.data4life.hc-securestore-kmp"
    )
}

fun RepositoryHandler.gitHub(project: Project) {
    Repository.github.forEach { (organization, repository, group) ->
        maven {
            setUrl("https://maven.pkg.github.com/$organization/$repository")
            credentials {
                username = project.project.findProperty("gpr.user") as String?
                    ?: System.getenv("PACKAGE_REGISTRY_USERNAME")
                password = project.project.findProperty("gpr.key") as String?
                    ?: System.getenv("PACKAGE_REGISTRY_TOKEN")
            }
            content {
                includeGroup(group)
            }
        }
    }
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

@Deprecated(message = "Should not be used if possible")
fun RepositoryHandler.jitPack() {
    maven("https://jitpack.io") {
        content {
            includeGroup("com.github.gesundheitscloud") // AppAuth
            includeGroup("com.github.ChuckerTeam.Chucker") // Chucker 3.3.0
        }
    }
}
