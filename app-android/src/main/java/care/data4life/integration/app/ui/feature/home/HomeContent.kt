/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import care.data4life.integration.app.R.string
import care.data4life.integration.app.ui.common.atom.Logo
import care.data4life.integration.app.ui.common.atom.PrimaryTextButton
import care.data4life.integration.app.ui.theme.IntegrationTheme
import care.data4life.integration.app.ui.theme.Spacing

@Composable
fun HomeContent(
    onLogoutClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier.testTag("HomeView"),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.S),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Logo()

            Spacer(modifier = Modifier.height(Spacing.M))

            Text("Home screen")

            Spacer(modifier = Modifier.height(Spacing.M))

            PrimaryTextButton(
                text = stringResource(id = string.home_logout_button_label),
                onClick = onLogoutClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeContent() {
    IntegrationTheme {
        HomeContent {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkWelcomeContent() {
    IntegrationTheme(darkTheme = true) {
        HomeContent {}
    }
}
