/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.gradle.integration.dependency

object Version {

    val gradlePlugin = GradlePlugin
    val multiplatform = Multiplatform
    val multiplatformTest = MultiplatformTest
    val jvm = Jvm
    val jvmTest = JvmTest
    val android = Android
    val androidTest = AndroidTest

    /**
     * [Kotlin](https://github.com/JetBrains/kotlin)
     */
    const val kotlin = "1.7.10"

    object GradlePlugin {
        const val kotlin = Version.kotlin
        const val android = "7.2.2"
        const val jUnit5Android = "1.8.2.0"

        /**
         * [Dexcount](https://github.com/KeepSafe/dexcount-gradle-plugin)
         */
        const val dexcount = "3.1.0"
    }

    object Multiplatform {

        val kotlin = Kotlin
        val d4l = D4L

        object Kotlin {
            /**
             * [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
             */
            const val coroutines = "1.6.4"
        }

        object D4L {
            /**
             * [D4L-SDK](https://github.com/d4l-data4life/hc-sdk-kmp)
             */
            const val hcSdk = "1.17.1"

            /**
             * [hc-util-sdk-kmp](https://github.com/d4l-data4life/hc-util-sdk-kmp)
             */
            const val utilSdk = "1.14.0"

            /**
             * [hc-auth-sdk-kmp](https://github.com/d4l-data4life/hc-auth-sdk-kmp)
             */
            const val authSdk = "1.16.0"

            /**
             * [D4L-FHIR-HELPER-SDK-KMP](https://github.com/d4l-data4life/hc-fhir-helper-sdk-kmp)
             */
            const val fhirHelper = "1.10.0"
        }

        /**
         * [Koin](https://github.com/InsertKoinIO/koin)
         */
        const val koin = "3.1.6"
    }

    object MultiplatformTest {
        /**
         * [mockk](http://mockk.io)
         */
        const val mockK = "1.12.5"
    }

    object Jvm {
        // Crypto
        /**
         * [BouncyCastle](http://www.bouncycastle.org/java.html)
         */
        const val bouncyCastle = "1.71"

        /**
         * [D4L-FHIR-SDK-JAVA](https://github.com/d4l-data4life/hc-fhir-sdk-java)
         */
        const val fhirSdk = "1.9.0"
    }

    object JvmTest {
        const val jUnit = "4.13.2"

        /**
         * [JSONassert](https://github.com/skyscreamer/JSONassert)
         */
        const val jsonAssert = "1.5.1"

        const val jacoco = "0.8.8"
    }

    object Android {

        val androidX = AndroidX
        val compose = Compose

        /**
         * [Android Desugar](https://github.com/google/desugar_jdk_libs)
         * [Information](https://developer.android.com/studio/write/java8-support)
         */
        const val androidDesugar = "1.1.5"

        object AndroidX {
            /**
             * [AndroidX](https://developer.android.com/jetpack/androidx)
             */
            const val appCompat = "1.5.0"
        }

        object Compose {
            const val compiler = "1.3.0"
            const val core = "1.3.0-alpha03"
            const val activity = "1.5.1"
            const val viewModel = "2.5.1"
            const val liveData = "1.2.1"
            const val navigation = "2.5.1"
        }

        /**
         * [Material Android](https://github.com/material-components/material-components-android)
         */
        const val material = "1.6.1"

        // Auth
        const val appAuth = "0.11.1"

        // Network
        /**
         * [okHttp](https://github.com/square/okhttp)
         */
        const val okHttp = "4.10.0"

        /**
         *[retrofit](https://github.com/square/retrofit)
         */
        const val retrofit = "2.9.0"

        /**
         *[gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
         */
        const val gson = "2.9.0"

        /**
         * [ThreeTen Android Backport](https://github.com/JakeWharton/ThreeTenABP)
         */
        const val threeTenABP = "1.4.0"
    }

    object AndroidTest {
        /**
         * [Android Testing](https://developer.android.com/testing)
         */
        const val androidXTest = "1.4.0"
        const val androidXTestCore = "1.5.0-alpha02"
        const val androidXTestOrchestrator = "1.4.1"
        const val androidXEspresso = "3.4.0"
        const val androidXUiAutomator = "2.2.0"
        const val androidXTestExtJUnit = "1.1.3"

        const val jUnit5Android = "5.8.2"
        const val jUnit5AndroidInstrumentation = "1.3.0"

        /**
         * [Robolectric](https://github.com/robolectric/robolectric)
         */
        const val robolectric = "4.7.3"

        // https://github.com/KakaoCup/Compose
        const val kakaoCompose = "0.1.0"
    }
}
