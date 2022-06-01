/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.welcome

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import care.data4life.integration.app.di.Di

@Composable
fun WelcomeView(
    openDashboard: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = StartActivityForResult(),
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                openDashboard()
            }
            else -> {
                // TODO "Failed to login with D4L"
            }
        }
    }

    val context = LocalContext.current
    val onLoginClicked = {
        launcher.launch(Di.data.authService.getLoginIntent(context))
    }

    WelcomeContent(
        onLoginClick = onLoginClicked,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeView() {
    WelcomeView() {}
}
