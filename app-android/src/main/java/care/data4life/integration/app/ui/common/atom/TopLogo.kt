/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import care.data4life.integration.app.R
import care.data4life.integration.app.ui.theme.Sizes

@Composable
fun TopLogo() {
    Image(
        painter = painterResource(id = R.drawable.d4l_logo),
        modifier = Modifier
            .testTag("TopLogo")
            .height(Sizes.topLogo),
        contentDescription = "TopLogo",
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
    )
}

@Preview
@Composable
fun TopLogoPreview() {
    TopLogo()
}
