/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import care.data4life.integration.app.test.BaseComposeTest
import care.data4life.integration.app.test.compose.assertScreenshotMatches
import care.data4life.integration.app.test.compose.setThemedContent
import care.data4life.integration.app.test.compose.setThemedScreenshotContent
import org.junit.jupiter.api.Test

@Suppress("TestFunctionName")
class LogoKtTest : BaseComposeTest() {

    @Test
    fun GIVEN_logo_WHEN_added_THEN_is_displayed() = extension.runComposeTest {
        // Given
        setThemedContent {
            Logo()
        }

        // When
        val logo = onNodeWithTag(testTagName)

        // Then
        logo.assertIsDisplayed()
    }

    @Test
    fun GIVEN_logo_WHEN_added_THEN_matches_screenshot() = extension.runComposeTest {
        // Given
        setThemedScreenshotContent {
            Logo()
        }

        // When
        val logo = onNodeWithTag(testTagName)

        // Then
        logo.assertIsDisplayed()
            .assertScreenshotMatches("atom", testTagName)
    }

    companion object {
        const val testTagName = "Logo"
    }
}
