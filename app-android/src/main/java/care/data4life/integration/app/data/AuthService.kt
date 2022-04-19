/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data

import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.data.DataContract.Wrapper

class AuthService(
    private val client: Wrapper.D4LClient
) : Service.Auth {

    override suspend fun isAuthorized(): Boolean = client.isAuthorized()
}
