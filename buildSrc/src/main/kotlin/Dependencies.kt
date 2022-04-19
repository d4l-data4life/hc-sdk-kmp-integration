/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

object Dependencies {

    object Android {
        // Kotlin
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"

        // Android
        const val desugar = "com.android.tools:desugar_jdk_libs:${Versions.androidDesugar}"

        object AndroidX {
            // AndroidX
            const val ktx = "androidx.core:core-ktx:${Versions.androidXKtx}"
            const val appCompat = "androidx.appcompat:appcompat:${Versions.androidXAppCompat}"
            const val browser = "androidx.browser:browser:${Versions.androidXBrowser}"
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.androidXConstraintLayout}"

            // Lifecylce
            const val lifecylceViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXLifecycle}"
            const val lifecylceCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidXLifecycle}"

            // Navigation
            const val navigationFragmentKtx =
                "androidx.navigation:navigation-fragment-ktx:${Versions.androidXNavigation}"
            const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidXNavigation}"

            object Compose {
                const val compiler = "androidx.compose.compiler:compiler:${Versions.androidXCompose}"
                const val runtime = "androidx.compose.runtime:runtime:${Versions.androidXCompose}"
                const val ui = "androidx.compose.ui:ui:${Versions.androidXCompose}"

                // Tooling support (Previews, etc.)
                const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.androidXCompose}"

                // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
                const val foundation = "androidx.compose.foundation:foundation:${Versions.androidXCompose}"

                // Material design
                const val material = "androidx.compose.material:material:${Versions.androidXCompose}"

                // Material design icons
                const val materialIconsCore =
                    "androidx.compose.material:material-icons-core:${Versions.androidXCompose}"
                const val materialIconsExtended =
                    "androidx.compose.material:material-icons-extended:${Versions.androidXCompose}"

                // Integration with activities
                const val activity = "androidx.activity:activity-compose:${Versions.androidXComposeActivity}"

                // Integration with ViewModels
                const val lifecycle =
                    "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.androidXComposeViewModel}"

                // Integration with observables
                const val liveData = "androidx.compose.runtime:runtime-livedata:${Versions.androidXComposeLiveData}"

                // Navigation
                const val navigation = "androidx.navigation:navigation-compose:${Versions.androidXComposeNavigation}"
            }
        }

        // Material
        const val material = "com.google.android.material:material:${Versions.material}"

        object D4L {
            const val hcSdk = "care.data4life.hc-sdk-kmp:sdk-android:${Versions.hcSdk}"
            const val utilSdk = "care.data4life.hc-util-sdk-kmp:util-android:${Versions.utilSdk}"
            const val fhirSdk = "care.data4life.hc-fhir-sdk-java:fhir-java:${Versions.fhirSdk}"
            const val authSdk = "care.data4life.hc-auth-sdk-kmp:auth-android:${Versions.authSdk}"
            const val fhirHelper = "care.data4life.hc-fhir-helper-sdk-kmp:fhir-helper-android:${Versions.fhirHelper}"
        }

        // Auth
        const val appAuth = "net.openid:appauth:${Versions.appAuth}"

        // Date
        const val threeTenABP = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenABP}"

        // Network
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.gson}"

        object Test {
            const val junit = "junit:junit:${Versions.testJUnit}"

            const val junit5 = "org.junit.jupiter:junit-jupiter-api:${Versions.testJUnit5Android}"
            const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.testJUnit5Android}"
            const val junit5EngineVintage = "org.junit.vintage:junit-vintage-engine:${Versions.testJUnit5Android}"
            const val junit5Parameterized = "org.junit.jupiter:junit-jupiter-params:${Versions.testJUnit5Android}"

            const val junit5AndroidInstrumentation = "de.mannodermaus.junit5:android-test-core:${Versions.testJUnit5AndroidInstrumentation}"
            const val junit5AndroidInstrumentationRuntime = "de.mannodermaus.junit5:android-test-runner:${Versions.testJUnit5AndroidInstrumentation}"

            const val testKotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
            const val testKotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

            const val mockk = "io.mockk:mockk:${Versions.testMockk}"
        }

        object AndroidTest {
            const val androidXTestCore = "androidx.test:core:${Versions.androidXTestCore}"
            const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
            const val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
            const val androidXTestOrchestrator = "androidx.test:orchestrator:${Versions.androidXTestOrchestrator}"

            const val androidXTestExtJUnit = "androidx.test.ext:junit:${Versions.androidXTestExtJUnit}"

            const val androidXTestEspressoCore = "androidx.test.espresso:espresso-core:${Versions.androidXEspresso}"
            const val androidXTestEspressoIntents =
                "androidx.test.espresso:espresso-intents:${Versions.androidXEspresso}"
            const val androidXTestEspressoWeb = "androidx.test.espresso:espresso-web:${Versions.androidXEspresso}"

            const val androidXTestUiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.androidXUiAutomator}"

            const val androidXTestComposeUi = "androidx.compose.ui:ui-test-junit4:${Versions.androidXCompose}"

            const val kakao = "com.agoda.kakao:kakao:${Versions.androidTestKakao}"

            const val mockk = "io.mockk:mockk-android:${Versions.testMockk}"
        }
    }
}
