/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import care.data4life.integration.app.test.compose.junit5.ComposeContentContext

abstract class BasePage(
    protected val composeContext: ComposeContentContext,
) {
    companion object {
        const val TIMEOUT_LONG = 1000 * 10L

        const val TIMEOUT_MEDIUM = 1000 * 1L

        const val TIMEOUT_SHORT = 500 * 1L
    }
}
