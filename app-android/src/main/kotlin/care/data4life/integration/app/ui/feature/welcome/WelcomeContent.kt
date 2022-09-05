/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.welcome

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import care.data4life.integration.app.R
import care.data4life.integration.app.ui.common.atom.Logo
import care.data4life.integration.app.ui.common.atom.PrimaryTextButton
import care.data4life.integration.app.ui.theme.D4LColors
import care.data4life.integration.app.ui.theme.IntegrationTheme
import care.data4life.integration.app.ui.theme.Spacing

@Composable
fun WelcomeContent(
    onLoginClick: () -> Unit,
    testTagName: String = "WelcomeContent"
) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier.testTag(testTagName),
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

            Text(
                text = stringResource(id = R.string.welcome_title),
                style = MaterialTheme.typography.h3,
            )

            Spacer(modifier = Modifier.height(Spacing.XS))

            Text(
                text = stringResource(id = R.string.welcome_description),
                style = MaterialTheme.typography.body1,
            )

            Spacer(modifier = Modifier.height(Spacing.M))

            Image(
                painter = painterResource(id = R.drawable.welcome_phone_lock),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(Spacing.S))

            Text(
                text = stringResource(id = R.string.welcome_app_environment_title),
                style = MaterialTheme.typography.body1,
            )

            Spacer(modifier = Modifier.height(Spacing.XS))

            Text(
                text = stringResource(id = R.string.sdk_environment).uppercase(),
                style = MaterialTheme.typography.h5,
                color = D4LColors.secondary
            )

            Spacer(modifier = Modifier.height(Spacing.M))

            PrimaryTextButton(
                text = stringResource(id = R.string.welcome_login_button_label),
                onClick = onLoginClick,
                testTagName = "${testTagName}ButtonLogin"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeContent() {
    IntegrationTheme {
        WelcomeContent({})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkWelcomeContent() {
    IntegrationTheme(darkTheme = true) {
        WelcomeContent({})
    }
}
