/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import care.data4life.integration.app.di.Di
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Action
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Event.NavigateToHome
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Event.NavigateToLogin
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.ViewModel
import care.data4life.integration.app.ui.feature.welcome.WelcomeView
import care.data4life.integration.app.ui.feature.welcome.WelcomeViewModel
import kotlinx.coroutines.flow.onEach

@Composable
fun WelcomeScreen(
    viewModel: ViewModel,
    openDashboard: () -> Unit
) {
    val viewState = viewModel.state.collectAsState()
    val onLoginClicked = { viewModel.onAction(Action.LoginClicked) }

    LaunchedEffect("events") {
        viewModel.events
            .onEach {
                when (it) {
                    NavigateToLogin -> TODO()
                    NavigateToHome -> TODO()
                }
            }
    }

    WelcomeView(
        onLoginClick = onLoginClicked,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen(viewModel = WelcomeViewModel(Di.data.authService))
}
