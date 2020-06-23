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

object Dependencies {

    object Android {
        // Kotlin
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"

        // Android
        const val androidDesugar = "com.android.tools:desugar_jdk_libs:${Versions.androidDesugar}"

        object AndroidX {
            // AndroidX
            const val ktx = "androidx.core:core-ktx:${Versions.androidXKtx}"
            const val appCompat = "androidx.appcompat:appcompat:${Versions.androidXAppCompat}"
            const val browser = "androidx.browser:browser:${Versions.androidXBrowser}"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidXConstraintLayout}"

            // Lifecylce
            const val lifecylceCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidXLifecycle}"
            const val lifecylceExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidXLifecycle}"

            // Navigation
            const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidXNavigation}"
            const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidXNavigation}"
        }

        // Material
        const val material = "com.google.android.material:material:${Versions.material}"

        object D4L {
            const val hcSdk = "de.gesundheitscloud.hc-sdk-android:sdk-android:${Versions.hcSdk}"
            const val fhirSdk = "de.gesundheitscloud:hc-fhir-android:${Versions.fhirSdk}"
            const val fhirHelper = "com.github.gesundheitscloud.sdk-fhir-helper-multiplatform:fhir-helper-android:${Versions.fhirHelper}"
        }

        //date
        const val threeTenABP = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenABP}"

        // Network
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.gson}"

        const val chuckerDebug = "com.github.ChuckerTeam.Chucker:library:${Versions.chucker}"
        const val checkerRelease = "com.github.ChuckerTeam.Chucker:library-no-op:${Versions.chucker}"

        object Test {
            const val junit = "junit:junit:${Versions.testJUnit}"

            const val testKotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
            const val testKotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        }

        object AndroidTest {
            const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
            const val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
            const val androidXTestOrchestrator = "androidx.test:orchestrator:${Versions.androidXTest}"

            const val androidXTestExtJUnit = "androidx.test.ext:junit:${Versions.androidXTestExtJUnit}"

            const val androidXTestEspressoCore = "androidx.test.espresso:espresso-core:${Versions.androidXEspresso}"
            const val androidXTestEspressoIntents = "androidx.test.espresso:espresso-intents:${Versions.androidXEspresso}"
            const val androidXTestEspressoWeb = "androidx.test.espresso:espresso-web:${Versions.androidXEspresso}"

            const val androidXTestUiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.androidXUiAutomator}"

            const val kakao = "com.agoda.kakao:kakao:${Versions.androidTestKakao}"
            const val kaspresso = "com.kaspersky.android-components:kaspresso:${Versions.androidTestKaspresso}"
        }
    }
}
