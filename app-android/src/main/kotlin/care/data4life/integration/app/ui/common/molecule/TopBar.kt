/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.molecule

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import care.data4life.integration.app.ui.common.atom.TopLogo
import care.data4life.integration.app.ui.theme.Sizes
import care.data4life.integration.app.ui.theme.Spacing

@Composable
fun TopBar(
    testTagName: String = "TopBar"
) {
    Surface(
        color = MaterialTheme.colors.primary,
        contentColor = contentColorFor(MaterialTheme.colors.primary),
        modifier = Modifier.testTag(testTagName)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Sizes.topBarHeight)
                .padding(start = Spacing.S, end = Spacing.S),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopLogo(testTagName = "TopBarLogo")
        }
    }
}
