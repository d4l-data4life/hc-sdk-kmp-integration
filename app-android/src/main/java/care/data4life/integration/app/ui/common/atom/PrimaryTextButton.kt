/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun PrimaryTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTagName: String = "PrimaryTextButton",
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .testTag(testTagName)
            .then(modifier),
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.button
        )
    }
}
