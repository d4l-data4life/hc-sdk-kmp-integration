/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import care.data4life.integration.app.ui.navigation.AppNavigation

@Composable
fun IntegrationApp() {
    val navController = rememberNavController()

    AppNavigation(
        controller = navController
    )
}
