/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data

import android.content.Context
import android.content.Intent
import care.data4life.integration.app.data.wrapper.D4LClientWrapperContract

interface DataContract {

    interface Wrapper {
        interface D4LClient : D4LClientWrapperContract {
            fun getLoginIntent(context: Context, scopes: Set<String>? = null): Intent
        }
    }

    interface Service {
        interface Auth {
            fun getLoginIntent(context: Context): Intent

            suspend fun isAuthorized(): Boolean
        }
    }
}
