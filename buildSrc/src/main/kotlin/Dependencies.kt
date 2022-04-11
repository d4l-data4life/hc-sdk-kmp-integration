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
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidXConstraintLayout}"

            // Lifecylce
            const val lifecylceViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXLifecycle}"
            const val lifecylceCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidXLifecycle}"

            // Navigation
            const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidXNavigation}"
            const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidXNavigation}"
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

            const val testKotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
            const val testKotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        }

        object AndroidTest {
            const val androidXTestCore = "androidx.test:core:${Versions.androidXTestCore}"
            const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
            const val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
            const val androidXTestOrchestrator = "androidx.test:orchestrator:${Versions.androidXTest}"

            const val androidXTestExtJUnit = "androidx.test.ext:junit:${Versions.androidXTestExtJUnit}"

            const val androidXTestEspressoCore = "androidx.test.espresso:espresso-core:${Versions.androidXEspresso}"
            const val androidXTestEspressoIntents = "androidx.test.espresso:espresso-intents:${Versions.androidXEspresso}"
            const val androidXTestEspressoWeb = "androidx.test.espresso:espresso-web:${Versions.androidXEspresso}"

            const val androidXTestUiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.androidXUiAutomator}"

            const val kakao = "com.agoda.kakao:kakao:${Versions.androidTestKakao}"
        }
    }
}
