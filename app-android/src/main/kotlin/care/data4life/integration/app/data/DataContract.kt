/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data

import android.content.Intent
import care.data4life.integration.app.data.wrapper.Result
import care.data4life.sdk.Data4LifeClient

interface DataContract {

    interface Wrapper {
        interface D4LClient {
            fun getRaw(): Data4LifeClient

            fun getLoginIntent(scopes: Set<String>? = null): Intent

            suspend fun finishLogin(authData: Intent): Result<Boolean>

            suspend fun isAuthorized(): Result<Boolean>
        }
    }

    interface Service {
        interface Auth {
            fun getLoginIntent(): Intent

            suspend fun finishLogin(authData: Intent): Result<Boolean>

            suspend fun isAuthorized(): Result<Boolean>
        }
    }
}
