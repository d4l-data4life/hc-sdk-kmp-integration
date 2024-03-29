/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import care.data4life.integration.app.ui.feature.home.HomeView
import care.data4life.integration.app.ui.feature.welcome.WelcomeView
import care.data4life.integration.app.ui.navigation.NavigationContract.AuthDestination
import care.data4life.integration.app.ui.navigation.NavigationContract.DashboardDestination
import care.data4life.integration.app.ui.navigation.NavigationContract.RootDestination

@Composable
fun AppNavigation(
    controller: NavHostController,
    startDestination: RootDestination = RootDestination.Authentication
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
        startDestination = AuthDestination.Welcome.route,
        route = RootDestination.Authentication.route
    ) {
        composable(route = AuthDestination.Welcome.route) {
            WelcomeView(
                openDashboard = {
                    controller.navigate(RootDestination.Dashboard.route)
                }
            )
        }
    }
}

fun NavGraphBuilder.addDashboard(
    controller: NavController
) {
    navigation(
        startDestination = DashboardDestination.Home.route,
        route = RootDestination.Dashboard.route
    ) {
        composable(route = DashboardDestination.Home.route) {
            HomeView(
                openAuthentication = {
                    controller.navigate(RootDestination.Authentication.route)
                }
            )
        }
    }
}
