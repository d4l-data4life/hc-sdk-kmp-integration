/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.navigation

interface NavigationContract {

    sealed class Destination(
        val route: String,
    ) {
        object Authentication : Destination("authentication") {
            object Welcome : Destination("welcmome")
        }

        object Dashboard : Destination("dashboard") {
            object Home : Destination("home")
        }
    }
}
