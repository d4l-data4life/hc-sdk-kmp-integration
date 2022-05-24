/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import care.data4life.integration.app.di.Di.Ui
import care.data4life.integration.app.ui.feature.welcome.WelcomeScreen
import care.data4life.integration.app.ui.navigation.NavigationContract.Destination

@Composable
fun AppNavigation(
    controller: NavHostController,
    startDestination: Destination = Destination.Authentication.Welcome
) {
    NavHost(
        navController = controller,
        startDestination = startDestination.route
    ) {

        navigation(
            startDestination = Destination.Authentication.Welcome.route,
            route = Destination.Authentication.route
        ) {
            composable(route = Destination.Authentication.Welcome.route) {
                WelcomeScreen(viewModel = Ui.welcomeViewModel)
            }
        }

        navigation(
            startDestination = Destination.Dashboard.Home.route,
            route = Destination.Dashboard.route
        ) {
            composable(route = Destination.Dashboard.Home.route) {
                Text("Home screen")
            }
        }
    }
}
