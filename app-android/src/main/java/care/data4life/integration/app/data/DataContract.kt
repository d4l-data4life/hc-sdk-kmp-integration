/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data

import care.data4life.integration.app.data.wrapper.D4LClientWrapperContract

interface DataContract {

    interface Wrapper {
        interface D4LClient : D4LClientWrapperContract
    }

    interface Service {
        interface Auth {
            suspend fun isAuthorized(): Boolean
        }
    }
}
