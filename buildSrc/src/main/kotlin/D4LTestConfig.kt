/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

data class D4LTestConfig(
        val user: UserConfig,
        val twillio: TwillioConfig
)

data class UserConfig(
        val email: String,
        val password: String,
        val phoneCountryCode: String,
        val phoneLocalNumber: String
)

data class TwillioConfig(
        val accountSid: String,
        val authSid: String,
        val authToken: String
)
