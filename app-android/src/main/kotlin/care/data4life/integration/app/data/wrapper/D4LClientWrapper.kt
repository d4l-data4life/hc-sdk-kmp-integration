/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data.wrapper

import android.content.Intent
import care.data4life.integration.app.data.DataContract.Wrapper.D4LClient
import care.data4life.sdk.Data4LifeClient

class D4LClientWrapper(
    client: Data4LifeClient? = createDefault()
) : D4LClient {

    private val _client: Data4LifeClient? = client

    private val client: Data4LifeClient
        get() = _client!!

    override fun getRaw(): Data4LifeClient {
        return client
    }

    override fun getLoginIntent(scopes: Set<String>?): Intent {
        return client.getLoginIntent(scopes)!!
    }

    override suspend fun finishLogin(authData: Intent): Result<Boolean> = awaitLegacyCallback { callback ->
        client.finishLogin(authData, callback)
    }

    override suspend fun isAuthorized(): Result<Boolean> = awaitLegacyListener { listener ->
        client.isUserLoggedIn(listener)
    }

    companion object {
        fun createDefault(): Data4LifeClient? = Data4LifeClient.getInstance()
    }
}
