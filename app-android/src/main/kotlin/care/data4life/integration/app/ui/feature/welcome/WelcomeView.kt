/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.welcome

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import care.data4life.integration.app.data.wrapper.Result.Failure
import care.data4life.integration.app.data.wrapper.Result.Success
import care.data4life.integration.app.di.Di
import care.data4life.sdk.log.Log
import kotlinx.coroutines.launch

@Composable
fun WelcomeView(
    openDashboard: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = StartActivityForResult(),
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                coroutineScope.launch {
                    when (val authResult = Di.data.authService.finishLogin(result.data!!)) {
                        is Success -> openDashboard()
                        is Failure -> {
                            Log.error(authResult.exception, "Failed to login")
                        }
                    }
                }
            }
            else -> {
                // TODO "Failed to login with D4L"
            }
        }
    }

    val onLoginClicked = {
        launcher.launch(Di.data.authService.getLoginIntent())
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
