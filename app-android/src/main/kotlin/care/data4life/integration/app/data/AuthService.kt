/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data

import android.content.Context
import android.content.Intent
import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.data.DataContract.Wrapper

class AuthService(
    private val client: Wrapper.D4LClient,
) : Service.Auth {

    override fun getLoginIntent(context: Context): Intent {
        return client.getLoginIntent(context)
    }

    override suspend fun isAuthorized(): Boolean = client.isAuthorized()
}
