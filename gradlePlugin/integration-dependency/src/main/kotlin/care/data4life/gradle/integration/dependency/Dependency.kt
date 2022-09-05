/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.gradle.integration.dependency

object Dependency {

    val kotlin = Kotlin
    val multiplatform = Multiplatform
    val multiplatformTest = MultiplatformTest
    val jvm = Jvm
    val jvmTest = JvmTest
    val android = Android
    val androidTest = AndroidTest

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Version.kotlin}"
    }

    object Multiplatform {

        val kotlin = Kotlin
        val d4l = D4L
        val koin = Koin

        object Kotlin {

            val stdLib = StdLib
            val coroutines = Coroutines

            object StdLib {
                const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Version.kotlin}"
                const val jdk = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
                const val jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
                const val js = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
                const val native = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
                const val android = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
            }

            object Coroutines {
                const val common =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.multiplatform.kotlin.coroutines}"
                const val android =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.multiplatform.kotlin.coroutines}"
                const val test =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.multiplatform.kotlin.coroutines}"
            }
        }

        object D4L {

            val util = Util
            val sdk = SDK
            val auth = Auth
            val fhirHelper = FhirHelper

            object Util {
                const val common = "care.data4life.hc-util-sdk-kmp:util:${Version.multiplatform.d4l.utilSdk}"
                const val android = "care.data4life.hc-util-sdk-kmp:util-android:${Version.multiplatform.d4l.utilSdk}"
                const val jvm = "care.data4life.hc-util-sdk-kmp:util-jvm:${Version.multiplatform.d4l.utilSdk}"
            }

            object SDK {
                const val android = "care.data4life.hc-sdk-kmp:sdk-android:${Version.multiplatform.d4l.hcSdk}"
                const val jvm = "care.data4life.hc-sdk-kmp:sdk-jvm:${Version.multiplatform.d4l.hcSdk}"
            }

            object Auth {
                const val android = "care.data4life.hc-auth-sdk-kmp:auth-android:${Version.multiplatform.d4l.authSdk}"
            }

            object FhirHelper {
                const val android =
                    "care.data4life.hc-fhir-helper-sdk-kmp:fhir-helper-android:${Version.multiplatform.d4l.fhirHelper}"
            }
        }

        object Koin {
            // https://github.com/InsertKoinIO/koin
            const val commonCore = "org.koin:koin-core:${Version.multiplatform.koin}"
            const val commonCoreExt = "org.koin:koin-core-ext:${Version.multiplatform.koin}"
            const val test = "org.koin:koin-test:${Version.multiplatform.koin}"
            const val androidScope = "org.koin:koin-androidx-scope:${Version.multiplatform.koin}"
            const val androidViewModel = "org.koin:koin-androidx-viewmodel:${Version.multiplatform.koin}"
            const val androidFragment = "org.koin:koin-androidx-fragment:${Version.multiplatform.koin}"
            const val androidExt = "org.koin:koin-androidx-ext:${Version.multiplatform.koin}"
            const val tor = "org.koin:koin-tor:${Version.multiplatform.koin}"
        }
    }

    object MultiplatformTest {

        val kotlin = Kotlin
        val mockK = MockK

        object Kotlin {
            const val common = "org.jetbrains.kotlin:kotlin-test-common:${Version.kotlin}"
            const val commonAnnotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Version.kotlin}"
            const val jvm = "org.jetbrains.kotlin:kotlin-test:${Version.kotlin}"
            const val jvmJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}"
        }

        object MockK {
            const val common = "io.mockk:mockk-common:${Version.multiplatformTest.mockK}"
            const val junit = "io.mockk:mockk:${Version.multiplatformTest.mockK}"
            const val androidTestInstrumentation = "io.mockk:mockk-android:${Version.multiplatformTest.mockK}"
        }
    }

    object Jvm {
        // Crypto
        const val bouncyCastleJdk15 = "org.bouncycastle:bcprov-jdk15on:${Version.jvm.bouncyCastle}"

        // FHIR
        const val fhirSdk = "care.data4life.hc-fhir-sdk-java:fhir-java:${Version.jvm.fhirSdk}"
    }

    object JvmTest {
        const val junit = "junit:junit:${Version.jvmTest.jUnit}"

        const val junit5 = "org.junit.jupiter:junit-jupiter-api:${Version.androidTest.jUnit5Android}"
        const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Version.androidTest.jUnit5Android}"
        const val junit5EngineVintage = "org.junit.vintage:junit-vintage-engine:${Version.androidTest.jUnit5Android}"
        const val junit5Parameterized = "org.junit.jupiter:junit-jupiter-params:${Version.androidTest.jUnit5Android}"

        const val testKotlin = "org.jetbrains.kotlin:kotlin-test:${Version.kotlin}"
        const val testKotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}"

        const val mockk = "io.mockk:mockk:${Version.multiplatformTest.mockK}"
    }

    object Android {

        val androidX = AndroidX

        // Kotlin
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
        const val kotlinCoroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.multiplatform.kotlin.coroutines}"

        // Android
        const val desugar = "com.android.tools:desugar_jdk_libs:${Version.android.androidDesugar}"

        object AndroidX {
            const val appCompat = "androidx.appcompat:appcompat:${Version.android.androidX.appCompat}"

            object Compose {
                const val compiler = "androidx.compose.compiler:compiler:${Version.android.compose.compiler}"
                const val runtime = "androidx.compose.runtime:runtime:${Version.android.compose.core}"
                const val ui = "androidx.compose.ui:ui:${Version.android.compose.core}"

                // Tooling support (Previews, etc.)
                const val uiTooling = "androidx.compose.ui:ui-tooling:${Version.android.compose.core}"

                // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
                const val foundation = "androidx.compose.foundation:foundation:${Version.android.compose.core}"

                // Material design
                const val material = "androidx.compose.material:material:${Version.android.compose.core}"

                // Material design icons
                const val materialIconsCore =
                    "androidx.compose.material:material-icons-core:${Version.android.compose.core}"
                const val materialIconsExtended =
                    "androidx.compose.material:material-icons-extended:${Version.android.compose.core}"

                // Integration with activities
                const val activity = "androidx.activity:activity-compose:${Version.android.compose.activity}"

                // Integration with ViewModels
                const val lifecycle =
                    "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.android.compose.viewModel}"

                // Integration with observables
                const val liveData = "androidx.compose.runtime:runtime-livedata:${Version.android.compose.liveData}"

                // Navigation
                const val navigation = "androidx.navigation:navigation-compose:${Version.android.compose.navigation}"
            }
        }

        // Material
        const val material = "com.google.android.material:material:${Version.android.material}"

        // Auth
        const val appAuth = "net.openid:appauth:${Version.android.appAuth}"

        // Date
        const val threeTenABP = "com.jakewharton.threetenabp:threetenabp:${Version.android.threeTenABP}"

        // Network
        const val okHttp = "com.squareup.okhttp3:okhttp:${Version.android.okHttp}"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.android.okHttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.android.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Version.android.gson}"
    }

    object AndroidTest {
        const val core = "androidx.test:core:${Version.androidTest.androidXTestCore}"
        const val runner = "androidx.test:runner:${Version.androidTest.androidXTest}"
        const val rules = "androidx.test:rules:${Version.androidTest.androidXTest}"
        const val orchestrator = "androidx.test:orchestrator:${Version.androidTest.androidXTestOrchestrator}"

        const val junitExt = "androidx.test.ext:junit:${Version.androidTest.androidXTestExtJUnit}"

        const val espressoCore = "androidx.test.espresso:espresso-core:${Version.androidTest.androidXEspresso}"
        const val espressoIntents = "androidx.test.espresso:espresso-intents:${Version.androidTest.androidXEspresso}"
        const val espressoWeb = "androidx.test.espresso:espresso-web:${Version.androidTest.androidXEspresso}"

        const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Version.androidTest.androidXUiAutomator}"

        const val robolectric = "org.robolectric:robolectric:${Version.androidTest.robolectric}"

        const val junit5AndroidInstrumentation =
            "de.mannodermaus.junit5:android-test-core:${Version.androidTest.jUnit5AndroidInstrumentation}"
        const val junit5AndroidInstrumentationRuntime =
            "de.mannodermaus.junit5:android-test-runner:${Version.androidTest.jUnit5AndroidInstrumentation}"

        const val composeUi = "androidx.compose.ui:ui-test-junit4:${Version.android.compose.core}"
        const val composeUiManifest = "androidx.compose.ui:ui-test-manifest:${Version.android.compose.core}"

        const val kakaoCompose = "io.github.kakaocup:compose:${Version.androidTest.kakaoCompose}"

        const val mockk = "io.mockk:mockk-android:${Version.multiplatformTest.mockK}"
    }
}
