/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun IntegrationApp() {
    val navController = rememberNavController()

    // TODO login check?

    NavHost(
        navController = navController,
        startDestination = Navigation.Route.WELCOME
    ) {
        composable(route = Navigation.Route.WELCOME) {
            Text("Welcome screen")
        }
        composable(route = Navigation.Route.HOME) {
            Text("Home screen")
        }
    }
}

object Navigation {

    object Arg {

    }

    object Route {
        const val WELCOME = "welcome"
        const val HOME = "home"
    }
}
