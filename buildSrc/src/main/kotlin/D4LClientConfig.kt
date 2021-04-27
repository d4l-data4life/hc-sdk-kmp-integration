/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

data class D4LClientConfig(
    val platform: String,
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
    DEVELOPMENT,
    STAGING,
    SANDBOX,
    PRODUCTION;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}
