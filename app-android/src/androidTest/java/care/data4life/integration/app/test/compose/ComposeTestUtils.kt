/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import care.data4life.integration.app.test.compose.junit5.ComposeContext
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

fun ComposeContext.setThemedScreenshotContent(
    content: @Composable () -> Unit
) {
    setContent {
        IntegrationTheme {
            Box(
                modifier = Modifier
                    .height(480.dp)
                    .width(300.dp),
            ) {
                content()
            }
        }
    }
}
