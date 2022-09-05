/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

data class D4LTestConfig(
    val user: UserConfig,
    val sinch: SinchConfig
)

data class UserConfig(
    val email: String,
    val password: String,
    val phoneCountryCode: String,
    val phoneLocalNumber: String,
    val s4hDataKeys: DataKeySet
)

data class DataKeySet(
    val local: String,
    val development: String,
    val staging: String,
    val sandbox: String,
    val production: String
)

data class SinchConfig(
    val servicePlanId: String,
    val authToken: String
)
