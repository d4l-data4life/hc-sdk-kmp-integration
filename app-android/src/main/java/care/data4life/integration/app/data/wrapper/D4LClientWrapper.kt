/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data.wrapper

import android.content.Context
import android.content.Intent
import care.data4life.integration.app.data.DataContract.Wrapper.D4LClient
import care.data4life.sdk.Data4LifeClient

class D4LClientWrapper(
    private val client: Data4LifeClient = createDefault()
) : D4LClient {

    override fun getLoginIntent(context: Context, scopes: Set<String>?): Intent {
        return client.getLoginIntent(context, scopes)
    }

    override suspend fun isAuthorized(): Boolean = callSuspendResultListenerWrapper { listener ->
        client.isUserLoggedIn(listener)
    }

    companion object {
        fun createDefault(): Data4LifeClient = Data4LifeClient.getInstance()
    }
}
