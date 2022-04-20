/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test.compose

import androidx.compose.runtime.Composable
import care.data4life.integration.app.ui.theme.IntegrationTheme

fun ComposeContext.setThemedContent(
    content: @Composable () -> Unit
) {
    setContent {
        IntegrationTheme {
            content()
        }
    }
}
