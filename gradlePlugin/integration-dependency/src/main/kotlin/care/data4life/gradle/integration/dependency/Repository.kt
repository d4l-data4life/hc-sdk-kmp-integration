/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.gradle.integration.dependency

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object Repository {
    private const val gitHubOrgD4l = "d4l-data4life"
    private const val gitHubOrgGesundheitsloud = "gesundheitscloud"

    val github = listOf(
        // GitHub organization, GitHub repository name, Maven dependency group
        listOf(gitHubOrgD4l, "hc-sdk-kmp", "care.data4life.hc-sdk-kmp"),
        listOf(gitHubOrgD4l, "hc-util-sdk-kmp", "care.data4life.hc-util-sdk-kmp"),
        listOf(gitHubOrgD4l, "hc-util-test-sdk-kmp", "care.data4life.hc-util-test-sdk-kmp"),
        listOf(gitHubOrgD4l, "hc-fhir-sdk-java", "care.data4life.hc-fhir-sdk-java"),
        listOf(gitHubOrgD4l, "hc-fhir-sdk-kmp", "care.data4life.hc-fhir-sdk-kmp"),
        listOf(gitHubOrgD4l, "hc-fhir-helper-sdk-kmp", "care.data4life.hc-fhir-helper-sdk-kmp"),
        listOf(gitHubOrgGesundheitsloud, "data-donation-sdk-native", "care.data4life.d4l-data-donation-sdk-kmp")
    )

    val d4l = listOf(
        // Maven dependency group
        "care.data4life.hc-sdk-kmp",
        "care.data4life.hc-util-sdk-kmp",
        "care.data4life.hc-util-test-sdk-kmp",
        "care.data4life.hc-auth-sdk-kmp",
        "care.data4life.hc-fhir-sdk-kmp",
        "care.data4life.hc-fhir-helper-sdk-kmp",
        "care.data4life.hc-securestore-kmp",
        "care.data4life.d4l-data-donation-sdk-kmp",
        "care.data4life.gradle.gitversion"
    )
}

fun RepositoryHandler.gitHub(project: Project) {
    Repository.github.forEach { (organization, repository, group) ->
        maven {
            setUrl("https://maven.pkg.github.com/$organization/$repository")
            credentials {
                username = project.project.findProperty("gpr.user") as String?
                    ?: System.getenv("PACKAGE_REGISTRY_DOWNLOAD_USERNAME")
                password = project.project.findProperty("gpr.key") as String?
                    ?: System.getenv("PACKAGE_REGISTRY_DOWNLOAD_TOKEN")
            }
            content {
                includeGroup(group)
            }
        }
    }
}

fun RepositoryHandler.d4l() {
    maven("https://raw.github.com/d4l-data4life/maven-releases/main/releases") {
        content {
            Repository.d4l.forEach { group ->
                includeGroup(group)
            }
        }
    }
    maven("https://raw.github.com/d4l-data4life/maven-snapshots/main/snapshots") {
        content {
            Repository.d4l.forEach { group ->
                includeGroup(group)
            }
        }
    }
    maven("https://raw.github.com/d4l-data4life/maven-features/main/features") {
        content {
            Repository.d4l.forEach { group ->
                includeGroup(group)
            }
        }
    }
}
