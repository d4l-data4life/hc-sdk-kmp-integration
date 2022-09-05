/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test

import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileNotFoundException

data class TestConfig(
    val sinch: Sinch,
    val user: User
)

data class User(
    val email: String,
    val password: String,
    val phoneCountryCode: String,
    val phoneLocalNumber: String,
    val s4hDataKeys: DataKeySet
) {
    val phoneNumber: String
        get() = phoneCountryCode + phoneLocalNumber

    fun getS4hDataKeyForEnvironment(environment: String): String {
        return when (environment) {
            "local" -> s4hDataKeys.local
            "development" -> s4hDataKeys.development
            "staging" -> s4hDataKeys.staging
            "sandbox" -> s4hDataKeys.sandbox
            "production" -> s4hDataKeys.production
            else -> "empty"
        }
    }
}

data class DataKeySet(
    val local: String,
    val development: String,
    val staging: String,
    val sandbox: String,
    val production: String
)

data class Sinch(
    val servicePlanId: String,
    val authToken: String
)

object TestConfigLoader {
    private const val FILE_NAME = "test_config.json"

    fun load(): TestConfig {
        try {
            val input = InstrumentationRegistry.getInstrumentation().context.assets.open(FILE_NAME)
            val json = input.bufferedReader().use(BufferedReader::readText)

            return Gson().fromJson(json, TestConfig::class.java)
        } catch (error: FileNotFoundException) {
            throw IllegalStateException("Please run './gradlew provideTestConfig' before running the tests", error)
        }
    }
}
