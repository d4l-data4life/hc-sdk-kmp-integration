/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data.wrapper

interface D4LClientWrapperContract {

    suspend fun isAuthorized(): Result<Boolean>
}
