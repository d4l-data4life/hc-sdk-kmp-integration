/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.test.compose.assertScreenshotMatches
import care.data4life.integration.app.test.compose.junit5.createAndroidComposeExtension
import care.data4life.integration.app.test.compose.setThemedContent
import care.data4life.integration.app.test.compose.setThemedScreenshotContent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@Suppress("TestFunctionName")
class TopLogoKtTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    @Test
    fun GIVEN_contentDescription_WHEN_displayed_THEN_has() = extension.runComposeTest {
        // Given
        setThemedContent {
            TopLogo()
        }

        // When
        val logo = onNodeWithContentDescription("TopLogo")

        // When/Then
        logo.assertIsDisplayed()
        logo.assertHeightIsEqualTo(32.dp)
    }

    @Test
    fun GIVEN_logo_WHEN_added_THEN_matches_screenshot() = extension.runComposeTest {
        // Given
        setThemedScreenshotContent {
            TopLogo()
        }

        // When
        val logo = onNodeWithTag("TopLogo")

        // Then
        logo.assertIsDisplayed()
            .assertScreenshotMatches("atom/TopLogo.png")
    }
}
