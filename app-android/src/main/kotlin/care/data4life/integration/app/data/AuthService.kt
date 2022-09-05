/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data

import android.content.Intent
import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.data.DataContract.Wrapper
import care.data4life.integration.app.data.wrapper.Result

class AuthService(
    private val client: Wrapper.D4LClient,
) : Service.Auth {

    override fun getLoginIntent(): Intent {
        return client.getLoginIntent()
    }

    override suspend fun finishLogin(authData: Intent): Result<Boolean> {
        return client.finishLogin(authData)
    }

    override suspend fun isAuthorized(): Result<Boolean> = client.isAuthorized()
}
