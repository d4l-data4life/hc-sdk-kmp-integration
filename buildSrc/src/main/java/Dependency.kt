/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2018, HPS Gesundheitscloud gGmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

object Dependency {

    val gradlePlugin = GradlePlugin

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:${Version.GradlePlugin.android}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.GradlePlugin.kotlin}"

        const val dexcount = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${Version.GradlePlugin.dexcount}"

        const val downloadTask = "de.undercouch:gradle-download-task:${Version.GradlePlugin.downloadTask}"
    }

    val kotlin = Kotlin

    object Kotlin {
        val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.kotlinCoroutines}"
    }

    val android = Android

    object Android {
        const val desugar = "com.android.tools:desugar_jdk_libs:${Version.androidDesugar}"

        val androidX = AndroidX
        object AndroidX {
            // AndroidX
            const val ktx = "androidx.core:core-ktx:${Version.androidXKtx}"
            const val appCompat = "androidx.appcompat:appcompat:${Version.androidXAppCompat}"
            const val browser = "androidx.browser:browser:${Version.androidXBrowser}"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.androidXConstraintLayout}"

            // Lifecylce
            const val lifecylceCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Version.androidXLifecycle}"
            const val lifecylceExtensions = "androidx.lifecycle:lifecycle-extensions:${Version.androidXLifecycle}"

            // Navigation
            const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Version.androidXNavigation}"
            const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Version.androidXNavigation}"
        }

        // Material
        const val material = "com.google.android.material:material:${Version.material}"

        val d4l = D4L
        object D4L {
            const val hcSdk = "de.gesundheitscloud.hc-sdk-android:sdk-android:${Version.hcSdk}"
            const val fhirSdk = "de.gesundheitscloud:hc-fhir-android:${Version.fhirSdk}"
            const val fhirHelper = "com.github.gesundheitscloud.sdk-fhir-helper-multiplatform:fhir-helper-android:${Version.fhirHelper}"
        }

        //date
        const val threeTenABP = "com.jakewharton.threetenabp:threetenabp:${Version.threeTenABP}"

        // Network
        val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
        val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
        val testOkHttpMockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.okHttp}"
        val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        val gson = "com.squareup.retrofit2:converter-gson:${Version.gson}"

        val chuckerDebug = "com.github.ChuckerTeam.Chucker:library:${Version.chucker}"
        val checkerRelease = "com.github.ChuckerTeam.Chucker:library-no-op:${Version.chucker}"
    }

    val test = Test
    object Test {
        const val testJunit = "junit:junit:${Version.testJUnit}"

        const val testKotlin = "org.jetbrains.kotlin:kotlin-test:${Version.kotlin}"
        const val testKotlinCommon = "org.jetbrains.kotlin:kotlin-test-common:${Version.kotlin}"
        const val testKotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}"
        const val testKotlinAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Version.kotlin}"

        const val testKotlinMockkCommon = "io.mockk:mockk-common:${Version.testMockk}"
        const val testKotlinMockkAndroid = "io.mockk:mockk-android:${Version.testMockk}"
    }

    val androidTest = AndroidTest
    object AndroidTest {
        const val androidXTestRunner = "androidx.test:runner:${Version.androidXTest}"
        const val androidXTestRules = "androidx.test:rules:${Version.androidXTest}"
        const val androidXTestOrchestrator = "androidx.test:orchestrator:${Version.androidXTest}"

        const val androidXTestExtJUnit = "androidx.test.ext:junit:${Version.androidXTestExtJUnit}"

        const val androidXTestEspressoCore = "androidx.test.espresso:espresso-core:${Version.androidXEspresso}"
        const val androidXTestEspressoIntents = "androidx.test.espresso:espresso-intents:${Version.androidXEspresso}"
        const val androidXTestEspressoWeb = "androidx.test.espresso:espresso-web:${Version.androidXEspresso}"

        const val androidXTestUiAutomator = "androidx.test.uiautomator:uiautomator:${Version.androidXUiAutomator}"

        const val androidXTestKakao = "com.agoda.kakao:kakao:${Version.androidXKakao}"
    }
}
