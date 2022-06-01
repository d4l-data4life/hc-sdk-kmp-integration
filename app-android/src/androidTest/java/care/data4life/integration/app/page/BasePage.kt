/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import care.data4life.integration.app.test.compose.junit5.ComposeContext

abstract class BasePage(
    protected val composeContext: ComposeContext,
) {
    companion object {
        const val TIMEOUT = 1000 * 10L

        const val TIMEOUT_SHORT = 500 * 1L
    }
}
