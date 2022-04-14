/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

object Versions {

    /**
     * [D4L-SDK](https://github.com/d4l-data4life/hc-sdk-kmp)
     */
    const val hcSdk = "1.15.1"

    /**
     * [D4L-FHIR-SDK-JAVA](https://github.com/d4l-data4life/hc-fhir-sdk-java)
     */
    const val fhirSdk = "1.6.3"

    /**
     * [D4L-FHIR-HELPER-SDK-KMP](https://github.com/d4l-data4life/hc-fhir-helper-sdk-kmp)
     */
    const val fhirHelper = "1.7.1"

    /**
     * [D4L-UTIL-SDK-KMP](https://github.com/d4l-data4life/hc-util-sdk-kmp)
     */
    const val utilSdk = "1.10.0"

    const val authSdk = "1.14.0"

    const val kotlin = "1.6.10"
    const val kotlinCoroutines = "1.6.1"

    object GradlePlugins {
        const val kotlin = Versions.kotlin
        const val android = "7.2.0-beta04"

        const val jUnit5Android = "1.8.2.0"

        /**
         * [Dexcount](https://github.com/KeepSafe/dexcount-gradle-plugin)
         */
        const val dexcount = "0.8.4"
    }

    // Android
    const val androidDesugar = "1.1.5"

    // AndroidX
    const val androidXKtx = "1.7.0"
    const val androidXAppCompat = "1.4.1"
    const val androidXBrowser = "1.4.0"

    const val androidXConstraintLayout = "2.1.3"

    const val androidXLifecycle = "2.4.1"
    const val androidXNavigation = "2.4.2"

    const val androidXCompose = "1.1.1"
    const val androidXComposeActivity = "1.4.0"
    const val androidXComposeViewModel = "2.4.1"
    const val androidXComposeLiveData = "1.1.1"
    const val androidXComposeNavigation = "2.4.2"

    // Material
    const val material = "1.5.0"

    // Auth
    const val appAuth = "0.10.0"

    // Network
    /**
     * [okHttp](https://github.com/square/okhttp)
     */
    const val okHttp = "4.9.1"

    /**
     *
     *[retrofit](https://github.com/square/retrofit)
     */
    const val retrofit = "2.9.0"

    /**
     *
     *[gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
     */
    const val gson = "2.9.0"

    /**
     * [ThreeTen Android Backport](https://github.com/JakeWharton/ThreeTenABP)
     */
    const val threeTenABP = "1.4.0"

    // Junit Test
    const val testJUnit = "4.13.2"

    const val testJUnit5Android = "5.8.2"
    const val testJUnit5AndroidInstrumentation = "1.3.0"

    // Android Test
    const val androidXTestCore = "1.4.0"
    const val androidXTest = "1.4.0"
    const val androidXTestOrchestrator = "1.4.1"
    const val androidXEspresso = "3.4.0"
    const val androidXUiAutomator = "2.2.0"
    const val androidXTestExtJUnit = "1.1.3"

    // https://github.com/agoda-com/Kakao
    const val androidTestKakao = "2.4.0"
}
