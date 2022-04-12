/*
 * Copyright (c) 2021 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object D4LColors {
    val primary = Color(0xFF000E7E)
    val primaryDark = Color(0xFF000051)
    val primaryAccent = Color(0xFFFF5E59)
    val grey = Color(0xFFAAAAAA)
}


val DarkColorPalette = darkColors(
    // TODO add branding Colors
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    secondaryVariant = Color(0xFF018786),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

val LightColorPalette = lightColors(
    primary = D4LColors.primary,
    primaryVariant = D4LColors.primaryDark,
    secondary = D4LColors.primaryAccent,
    secondaryVariant = Color(0xFF018786),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)
