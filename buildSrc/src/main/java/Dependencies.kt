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

object GradlePlugins {
    const val android = "com.android.tools.build:gradle:${Versions.GradlePlugin.android}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.GradlePlugin.kotlin}"

    const val dexcount = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${Versions.GradlePlugin.dexcount}"

    const val downloadTask = "de.undercouch:gradle-download-task:${Versions.GradlePlugin.downloadTask}"
}


object Libraries {
    // HC sdk
    const val hcSdk = "de.gesundheitscloud.hc-sdk-android:sdk-android:${Versions.hcSdk}"
    const val fhirSdk = "de.gesundheitscloud:hc-fhir-android:${Versions.fhirSdk}"
    const val fhirHelper = "com.github.gesundheitscloud.sdk-fhir-helper-multiplatform:fhir-helper-android:${Versions.fhirHelper}"

    //date
    const val threeTenABP = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenABP}"

    // Kotlin
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinStdLibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
    const val kotlinStdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"

    // AndroidX
    const val androidXKtx = "androidx.core:core-ktx:${Versions.androidXKtx}"
    const val androidXAppCompat = "androidx.appcompat:appcompat:${Versions.androidXAppCompat}"
    const val androidXBrowser = "androidx.browser:browser:${Versions.androidXBrowser}"
    const val androidXConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidXConstraintLayout}"

    // Lifecylce
    const val androidXLifecylceCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidXLifecycle}"
    const val androidXLifecylceExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidXLifecycle}"

    // Navigation
    const val androidXNavigationFragmentKtx = "android.arch.navigation:navigation-fragment-ktx:${Versions.androidXNavigation}"
    const val androidXNavigationUiKtx = "android.arch.navigation:navigation-ui-ktx:${Versions.androidXNavigation}"

    // Material
    const val material = "com.google.android.material:material:${Versions.material}"

    // Google
    const val googlePlayServicesBase = "com.google.android.gms:play-services-base:${Versions.googlePlayServices}"

    // Injection
    const val koinCore = "org.koin:koin-core:${Versions.koin}"
    const val testKoin = "org.koin:koin-test:${Versions.koin}"

    // Network
    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    val testOkHttpMockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val gson = "com.squareup.retrofit2:converter-gson:${Versions.gson}"
    val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val chuckerDebug = "com.github.ChuckerTeam.Chucker:library:${Versions.chucker}"
    val checkerRelease = "com.github.ChuckerTeam.Chucker:library-no-op:${Versions.chucker}"


    // Test
    const val testJunit = "junit:junit:${Versions.testJUnit}"

    const val testKotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    const val testKotlinCommon = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
    const val testKotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val testKotlinAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"

    const val testKotlinMockkCommon = "io.mockk:mockk-common:${Versions.testMockk}"
    const val testKotlinMockkAndroid = "io.mockk:mockk-android:${Versions.testMockk}"

    // Android Test
    const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
    const val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
    const val androidXTestOrchestrator = "androidx.test:orchestrator:${Versions.androidXTest}"

    const val androidXTestExtJUnit = "androidx.test.ext:junit:${Versions.androidXTestExtJUnit}"

    const val androidXTestEspressoCore = "androidx.test.espresso:espresso-core:${Versions.androidXEspresso}"
    const val androidXTestEspressoIntents = "androidx.test.espresso:espresso-intents:${Versions.androidXEspresso}"
    const val androidXTestEspressoWeb = "androidx.test.espresso:espresso-web:${Versions.androidXEspresso}"

    const val androidXTestUiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.androidXUiAutomator}"

    //  const val androidXTestKakao = "com.agoda.kakao:kakao:${Versions.androidXKakao}"
    const val androidXTestKakao = "com.github.wmontwe:Kakao:${Versions.androidXKakao}"

    //  Twilio
    const val twilioSDK = "com.twilio.sdk'"

}
