/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import care.data4life.integration.app.R
import care.data4life.integration.app.ui.common.LoginButton
import care.data4life.integration.app.ui.theme.D4LColors
import care.data4life.integration.app.ui.theme.IntegrationTheme
import care.data4life.integration.app.ui.theme.Spacing

@Composable
fun WelcomeView(
    onLoginClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.d4l_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

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

            LoginButton(
                onClick = onLoginClick
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_login_button_label).uppercase(),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeView() {
    IntegrationTheme {
        WelcomeView {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkWelcomeView() {
    IntegrationTheme(darkTheme = true) {
        WelcomeView {}
    }
}
