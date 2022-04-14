/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test.compose

interface ComposeExtension {
    fun runComposeTest(block: ComposeContext.() -> Unit)
}
