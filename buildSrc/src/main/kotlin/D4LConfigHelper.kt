/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

import com.google.gson.GsonBuilder
import java.io.File

/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, HPS Gesundheitscloud gGmbH
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

object D4LConfigHelper {

    private const val FILE_NAME_CLIENT_CONFIG_ANDROID = "d4l-client-config-android.json"
    private const val FILE_NAME_TEST_CONFIG_ANDROID = "d4l-test-config-android.json"

    private const val ENV_CLIENT_CONFIG_ANDROID = "D4L_CLIENT_CONFIG_ANDROID"
    private const val ENV_TEST_CONFIG_ANDROID = "D4L_TEST_CONFIG_ANDROID"

    private fun gson() = GsonBuilder().setPrettyPrinting().create()

    private fun loadConfig(path: String, fileName: String, envVarName: String): String {
        return try {
            File(path, fileName).readText()
        } catch (e: Exception) {
            try {
                System.getenv(envVarName)
            } catch (e: Exception) {
                null
            }
        } ?: throw IllegalStateException("Config file not found here: $path/$fileName nor environment variable $envVarName was set")
    }

    fun loadClientConfigAndroid(path: String): D4LClientConfig {
        val configJson = loadConfig(path, FILE_NAME_CLIENT_CONFIG_ANDROID, ENV_CLIENT_CONFIG_ANDROID)
        return gson().fromJson(configJson, D4LClientConfig::class.java)
    }

    fun toJson(d4lClientConfig: D4LClientConfig): String {
        return gson().toJson(d4lClientConfig)
    }

    fun loadTestConfigAndroid(path: String): D4LTestConfig {
        val configJson = loadConfig(path, FILE_NAME_TEST_CONFIG_ANDROID, ENV_TEST_CONFIG_ANDROID)
        return gson().fromJson(configJson, D4LTestConfig::class.java)
    }

    fun toJson(d4lTestConfig: D4LTestConfig): String {
        return gson().toJson(d4lTestConfig)
    }
}
