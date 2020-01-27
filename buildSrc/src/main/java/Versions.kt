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

object Versions {
    const val hcSdk = "1.5.2"
    const val fhirSdk = "0.5.0"
    const val fhirHelper = "1.2.3"

    const val kotlin = "1.3.61"
    const val kotlinCoroutines = "1.3.3"

    object GradlePlugin {
        const val kotlin = Versions.kotlin
        const val android = "3.5.3"

        /**
         * [Dexcount](https://github.com/KeepSafe/dexcount-gradle-plugin)
         */
        const val dexcount = "0.8.4"

        /**
         * [Gradle DownloadTask](https://github.com/michel-kraemer/gradle-download-task)
         */
        const val downloadTask = "3.4.3"
    }

    // AndroidX
    const val androidXKtx = "1.0.1"
    const val androidXAppCompat = "1.1.0"
    const val androidXBrowser = "1.0.0"

    const val androidXConstraintLayout = "2.0.0-alpha3"

    const val androidXLifecycle = "2.0.0"
    const val androidXNavigation = "1.0.0-beta02"

    // Material
    const val material = "1.0.0"

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
    const val threeTenABP = "1.2.2"

    // Injection
    const val koin = "1.0.1"


    // Junit Test
    const val testJUnit = "4.12"

    /**
     * [mockk](http://mockk.io)
     */
    const val testMockk = "1.8.12"


    // Android Test
    const val androidXTest = "1.1.1"
    const val androidXEspresso = "3.1.1"
    const val androidXUiAutomator = "2.2.0"
    const val androidXTestExtJUnit = "1.1.1"

    // https://github.com/agoda-com/Kakao = 1.4.0
    // androidTestImplementation("com.agoda.kakao:kakao:1.4.0")
    // currently patched version for AndroidX from https://github.com/wmontwe/Kakao
    const val androidXKakao = "1.4.0-androidx"

    // Twilio
    const val twilioSdk = "4.4.5"
}
