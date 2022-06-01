/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.navigation

interface NavigationContract {

    abstract class Destination(
        val route: String
    )

    sealed class RootDestination(
        route: String
    ) : Destination(route) {
        object Authentication : RootDestination("authentication")
        object Dashboard : RootDestination("dashboard")
    }

    sealed class AuthDestination(
        route: String
    ) : Destination(route) {
        object Welcome : AuthDestination("welcome")
    }

    sealed class DashboardDestination(
        route: String
    ) : Destination(route) {
        object Home : DashboardDestination("home")
    }
}
