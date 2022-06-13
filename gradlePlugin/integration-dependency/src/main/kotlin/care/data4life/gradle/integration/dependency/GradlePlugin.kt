/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.gradle.integration.dependency

object GradlePlugin {
    const val android = "com.android.tools.build:gradle:${Version.GradlePlugin.android}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.GradlePlugin.kotlin}"
    const val jUnit5Android = "de.mannodermaus.gradle.plugins:android-junit5:${Version.GradlePlugin.jUnit5Android}"
}
