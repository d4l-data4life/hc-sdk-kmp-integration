/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

import Environment.PRODUCTION

data class D4LClientConfig(
    val platform: String,
    val configs: Map<Environment, ClientConfig>
) {
    operator fun get(environment: Environment): ClientConfig {
        return configs.getValue(environment)
    }

    fun toConfigMap(environment: Environment, debug: Boolean? = null): Map<String, String> {
        return mutableMapOf(
            "platform" to platform,
            "environment" to environment.toString(),
            "clientId" to get(environment).id,
            "clientSecret" to get(environment).secret,
            "redirectScheme" to get(environment).redirectScheme,
        ).also {
            if (environment == PRODUCTION && debug == null) {
                it["debug"] = "false"
            } else if (debug != null) {
                it["debug"] = debug.toString()
            }
        }
    }
}

data class ClientConfig(
    val id: String,
    val secret: String,
    val redirectScheme: String
)

enum class Environment {
    LOCAL,
    DEVELOPMENT,
    STAGING,
    SANDBOX,
    PRODUCTION;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}
