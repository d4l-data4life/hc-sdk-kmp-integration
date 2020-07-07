/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

data class D4LClientConfig(
        val configs: Map<Environment, ClientConfig>
) {
    operator fun get(environment: Environment): ClientConfig {
        return configs.getValue(environment)
    }
}

data class ClientConfig(
        val id: String,
        val secret: String,
        val redirectScheme: String
)

enum class Environment {
    LOCAL,
    DEVELOP,
    STAGING,
    SANDBOX,
    PRODUCTION
}
