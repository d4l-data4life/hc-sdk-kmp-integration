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
    val android = "com.android.tools.build:gradle:${Versions.GradlePlugin.android}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.GradlePlugin.kotlin}"

    val dexcount = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${Versions.GradlePlugin.dexcount}"

    val downloadTask = "de.undercouch:gradle-download-task:${Versions.GradlePlugin.downloadTask}"
}


object Libraries {
    // Kotlin
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val kotlinStdLibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
    val kotlinStdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"

    // AndroidX
    val androidXKtx = "androidx.core:core-ktx:${Versions.androidXKtx}"
    val androidXAppCompat = "androidx.appcompat:appcompat:${Versions.androidXAppCompat}"
    val androidXBrowser = "androidx.browser:browser:${Versions.androidXBrowser}"
    val androidXConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidXConstraintLayout}"

    // Lifecylce
    val androidXLifecylceCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidXLifecycle}"
    val androidXLifecylceExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidXLifecycle}"

    // Navigation
    val androidXNavigationFragmentKtx = "android.arch.navigation:navigation-fragment-ktx:${Versions.androidXNavigation}"
    val androidXNavigationUiKtx = "android.arch.navigation:navigation-ui-ktx:${Versions.androidXNavigation}"
    val androidXNavigationTesting = "android.arch.navigation:navigation-testing:${Versions.androidXNavigation}"

    // Material
    val material = "com.google.android.material:material:${Versions.material}"

    // Google
    val googlePlayServicesBase = "com.google.android.gms:play-services-base:${Versions.googlePlayServices}"

    // Date
    val threeTenABP = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenABP}"

    // Injection
    val koinCore = "org.koin:koin-core:${Versions.koin}"
    val testKoin = "org.koin:koin-test:${Versions.koin}"


    // Test
    val testJunit = "junit:junit:${Versions.testJUnit}"

    val testKotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    val testKotlinCommon = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
    val testKotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    val testKotlinAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"

    val testKotlinMockkCommon = "io.mockk:mockk-common:${Versions.testMockk}"
    val testKotlinMockkAndroid = "io.mockk:mockk-android:${Versions.testMockk}"


    // Android Test
    val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
    val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
    val androidXTestOrchestrator = "androidx.test:orchestrator:${Versions.androidXTest}"

    val androidXTestEspressoCore = "androidx.test.espresso:espresso-core:${Versions.androidXEspresso}"
    val androidXTestEspressoIntents = "androidx.test.espresso:espresso-intents:${Versions.androidXEspresso}"
    val androidXTestEspressoWeb = "androidx.test.espresso:espresso-web:${Versions.androidXEspresso}"

    val androidXTestUiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.androidXUiAutomator}"

//    val androidXTestKakao = "com.agoda.kakao:kakao:${Versions.androidXKakao}"
    val androidXTestKakao = "com.github.wmontwe:Kakao:${Versions.androidXKakao}"
}
