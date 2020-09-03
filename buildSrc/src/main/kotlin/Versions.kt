/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

object Versions {
    const val hcSdk = "1.6.0"

    /**
     * [D4L-FHIR-SDK]https://github.com/gesundheitscloud/hc-fhir-android)
     */
    const val fhirSdk = "0.6.0"
    const val fhirHelper = "1.2.4"

    const val kotlin = "1.3.72"
    const val kotlinCoroutines = "1.3.6"

    object GradlePlugins {
        const val kotlin = Versions.kotlin
        const val android = "4.0.1"

        /**
         * [Dexcount](https://github.com/KeepSafe/dexcount-gradle-plugin)
         */
        const val dexcount = "0.8.4"

        /**
         * [DependencyUpdates](https://github.com/ben-manes/gradle-versions-plugin)
         */
        const val dependencyUpdates = "0.28.0"
    }

    // Android
    const val androidDesugar = "1.0.5"

    // AndroidX
    const val androidXKtx = "1.2.0"
    const val androidXAppCompat = "1.1.0"
    const val androidXBrowser = "1.2.0"

    const val androidXConstraintLayout = "2.0.0-beta4"

    const val androidXLifecycle = "2.1.0"
    const val androidXNavigation = "2.2.0"

    // Material
    const val material = "1.1.0"

    // Google
    const val googlePlayServices = "15.0.1"

    // Network
    /**
     * [okHttp](https://github.com/square/okhttp)
     */
    const val okHttp = "4.3.1"

    /**
     *
     *[retrofit](https://github.com/square/retrofit)
     */
    const val retrofit = "2.7.1"

    /**
     *
     *[gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
     */
    const val gson = "2.7.1"

    /**
     *
     *[chucker inspector](//https://github.com/ChuckerTeam/chucker/tree/develop)
     */
    const val chucker = "3.1.1"

    /**
     * [ThreeTen Android Backport](https://github.com/JakeWharton/ThreeTenABP)
     */
    const val threeTenABP = "1.2.4"


    // Junit Test
    const val testJUnit = "4.13"


    // Android Test
    const val androidXTestCore = "1.0.0"
    const val androidXTest = "1.1.0"
    const val androidXEspresso = "3.1.1"
    const val androidXUiAutomator = "2.2.0"
    const val androidXTestExtJUnit = "1.1.0"

    // https://github.com/agoda-com/Kakao
    const val androidTestKakao = "2.3.2"

    // https://github.com/KasperskyLab/Kaspresso
    const val androidTestKaspresso = "1.1.0"
}
