/*
 * Copyright (c) 2021 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun IntegrationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = IntegrationTypography,
        shapes = IntegrationShapes,
        content = content
    )
}
