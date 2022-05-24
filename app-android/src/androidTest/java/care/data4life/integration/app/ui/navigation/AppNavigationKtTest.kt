/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.test.compose.junit5.createAndroidComposeExtension
import care.data4life.integration.app.test.compose.setThemedContent
import care.data4life.integration.app.ui.navigation.NavigationContract.AuthDestination
import care.data4life.integration.app.ui.navigation.NavigationContract.DashboardDestination
import care.data4life.integration.app.ui.navigation.NavigationContract.Destination
import care.data4life.integration.app.ui.navigation.NavigationContract.RootDestination
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

@Suppress("TestFunctionName")
class AppNavigationKtTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    @Test
    fun GIVEN_app_navigation_WHEN_displayed_THEN_open_default_authentication_destination() = extension.runComposeTest {
        // Given
        lateinit var navController: NavHostController

        // When
        setThemedContent {
            navController = rememberNavController()
            AppNavigation(controller = navController)
        }

        // Then
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(AuthDestination.Welcome.route, route)
    }

    @Test
    fun GIVEN_app_navigation_WHEN_navigate_dashboard_THEN_open_default_destination() = extension.runComposeTest {
        // Given
        lateinit var navController: NavHostController
        val destinationRoute = RootDestination.Dashboard.route

        // When
        setThemedContent {
            navController = rememberNavController()
            AppNavigation(controller = navController)

            navController.navigate(destinationRoute)
        }

        // Then
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(DashboardDestination.Home.route, route)
    }
}
