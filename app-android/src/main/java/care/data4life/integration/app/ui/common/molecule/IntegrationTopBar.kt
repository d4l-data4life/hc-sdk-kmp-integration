/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.molecule

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import care.data4life.integration.app.ui.common.atom.TopLogo
import care.data4life.integration.app.ui.theme.Sizes
import care.data4life.integration.app.ui.theme.Spacing

@Composable
fun IntegrationTopBar() {
    Surface(
        color = MaterialTheme.colors.primary,
        contentColor = contentColorFor(MaterialTheme.colors.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Sizes.topBarHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(Spacing.S))

            TopLogo()
        }
    }
}
