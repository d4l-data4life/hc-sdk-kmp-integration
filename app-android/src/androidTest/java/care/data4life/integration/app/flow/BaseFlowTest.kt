/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.flow

import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.test.compose.junit5.ComposeContext
import care.data4life.integration.app.test.compose.junit5.createAndroidComposeExtension
import care.data4life.integration.app.ui.IntegrationApp
import care.data4life.integration.app.ui.theme.IntegrationTheme
import org.junit.jupiter.api.extension.RegisterExtension

abstract class BaseFlowTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    protected fun ComposeContext.setMainContent() {
        setContent {
            IntegrationTheme {
                IntegrationApp()
            }
        }
    }
}
