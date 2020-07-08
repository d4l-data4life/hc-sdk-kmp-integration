/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.testUtils

import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileNotFoundException

data class TestConfig(
        val user: User,
        val twillio: TwillioConfig
)

data class User(
        val email: String,
        val password: String,
        val phoneCountryCode: String,
        val phoneLocalNumber: String
) {
    val phoneNumber: String
        get() = phoneCountryCode + phoneLocalNumber
}

data class TwillioConfig(
        val accountSid: String,
        val authSid: String,
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
            throw IllegalStateException("Please run '/gradlew provideAndroidTestConfig' before running the tests", error)
        }
    }
}
