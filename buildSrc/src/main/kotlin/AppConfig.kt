/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

object AppConfig {

    const val version = "1.0"
    const val group = "care.data4life.integration.app"

    val androidConfig = AndroidConfig

    object AndroidConfig {
        const val minSdkVersion = 23
        const val compileSdkVersion = 33
        const val targetSdkVersion = 33

        const val versionCode = 1
        const val versionName = version

        const val applicationId = group
    }
}
