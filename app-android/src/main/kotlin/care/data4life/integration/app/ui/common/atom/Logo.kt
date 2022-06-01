/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import care.data4life.integration.app.R

@Composable
fun Logo(
    testTagName: String = "Logo"
) {
    Image(
        painter = painterResource(id = R.drawable.d4l_logo),
        modifier = Modifier
            .testTag(testTagName)
            .fillMaxWidth(),
        contentDescription = "Logo"
    )
}
