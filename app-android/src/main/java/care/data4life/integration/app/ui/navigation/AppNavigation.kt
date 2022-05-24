/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
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
        addAuthorization(controller)
        addDashboard(controller)

    }
}

fun NavGraphBuilder.addAuthorization(
    controller: NavController
) {
    navigation(
        startDestination = Destination.Authentication.Welcome.route,
        route = Destination.Authentication.route
    ) {
        composable(route = Destination.Authentication.Welcome.route) {
            WelcomeScreen(
                viewModel = Ui.welcomeViewModel,
                openDashboard = {
                    controller.navigate(Destination.Dashboard.route)
                }
            )
        }
    }
}

fun NavGraphBuilder.addDashboard(
    controller: NavController
) {
    navigation(
        startDestination = Destination.Dashboard.Home.route,
        route = Destination.Dashboard.route
    ) {
        composable(route = Destination.Dashboard.Home.route) {
            Text("Home screen")
        }
    }
}
