/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.home

import androidx.compose.runtime.Composable

@Composable
fun HomeView(
    openAuthentication: () -> Unit
) {
    val onLogoutClicked = {
        openAuthentication()
    }

    HomeContent(
        onLogoutClick = onLogoutClicked
    )
}
