/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.unit.dp
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.R
import care.data4life.integration.app.test.compose.createAndroidComposeExtension
import care.data4life.integration.app.ui.theme.IntegrationTheme
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@Suppress("TestFunctionName")
class TopLogoTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    @Test
    fun GIVEN_contentDescription_WHEN_displayed_THEN_has() = extension.runComposeTest {
        // Given
        setContent {
            IntegrationTheme {
                TopLogo()
            }
        }

        // When
        val logo = onNodeWithContentDescription("Logo")

        // When/Then
        logo.assertIsDisplayed()
        logo.assertHeightIsEqualTo(32.dp)
    }
}
