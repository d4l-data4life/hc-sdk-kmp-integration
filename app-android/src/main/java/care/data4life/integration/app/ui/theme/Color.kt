/*
 * Copyright (c) 2021 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object D4LColors {
    val primary = Color(0xFF000080)
    val primaryLight = Color(0xFF333399)
    val primaryLighter = Color(0xFF999FCB)
    val primaryLightest = Color(0xFFCCCFE5)
    val primaryExtraLightest = Color(0xFFE5E6F2)

    val primaryDark = Color(0xFF000051)

    val secondary = Color(0xFFFF5E59)
    val secondaryLight = Color(0xFFFFA19D)
    val secondaryLighter = Color(0xFFFFD0CE)
    val secondaryLightest = Color(0xFFFFE8E6)

    val tertiary = Color(0xFFFFD2C3)
    val tertiaryLight = Color(0xFFFFDBCF)
    val tertiaryLighter = Color(0xFFFFEDE7)
    val tertiaryLightest = Color(0xFFFFF6F3)

    val violet = Color(0xFFC8AEBF)
    val blue = Color(0xFFBDD4F0)
    val green = Color(0xFFD7EECB)
    val lime = Color(0xFFEBF0C8)
    val yellow = Color(0xFFFFEBAA)

    val success = Color(0xFF00855F)
    val alarm = Color(0xFFD53939)

    val neutral = Color(0xFF595757)
    val neutralLight = Color(0xFFACABA8)
    val neutralLighter = Color(0xFFDEDCDA)
    val neutralLightest = Color(0xFFF2F0F1)
    val neutralExtraLightest = Color(0xFFFAF8F8)
}


val DarkColorPalette = darkColors(
    // TODO add branding Colors
    primary = D4LColors.primaryLight,
    primaryVariant = D4LColors.primaryLighter,
    secondary = D4LColors.secondaryLight,
    secondaryVariant = D4LColors.secondaryLighter,
    background = D4LColors.neutral,
    surface = Color(0xFF121212),
    error = D4LColors.alarm,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

val LightColorPalette = lightColors(
    primary = D4LColors.primary,
    primaryVariant = D4LColors.primaryDark,
    secondary = D4LColors.secondary,
    secondaryVariant = D4LColors.secondaryLight,
    background = D4LColors.neutralLightest,
    surface = Color.White,
    error = D4LColors.alarm,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)
