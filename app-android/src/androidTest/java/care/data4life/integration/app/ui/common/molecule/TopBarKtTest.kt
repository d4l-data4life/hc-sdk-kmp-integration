/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.molecule

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.test.compose.createAndroidComposeExtension
import care.data4life.integration.app.ui.theme.IntegrationTheme
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@Suppress("TestFunctionName")
class TopBarKtTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    @Test
    fun GIVEN_topBar_WHEN_displayed_THEN_height_and_logo_present() = extension.runComposeTest {
        // Given
        setContent {
            IntegrationTheme {
                TopBar()
            }
        }

        // When
        val topBar = onNodeWithTag("TopBar")
        val topLogo = onNodeWithTag("TopLogo")

        // Then
        topBar.assertIsDisplayed()
            .assertHeightIsEqualTo(56.dp)

        topLogo.assertIsDisplayed()

    }
}
