/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import care.data4life.integration.app.test.BaseComposeTest
import care.data4life.integration.app.test.compose.assertScreenshotMatches
import care.data4life.integration.app.test.compose.setThemedContent
import care.data4life.integration.app.test.compose.setThemedScreenshotContent
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@Suppress("TestFunctionName")
class PrimaryTextButtonKtTest : BaseComposeTest() {

    @Test
    fun GIVEN_text_WHEN_displayed_THEN_its_uppercase() = extension.runComposeTest {
        // Given
        val text = "text"

        setThemedContent {
            PrimaryTextButton(
                text = text,
                onClick = { }
            )
        }

        // When
        val button = onNodeWithText("TEXT")

        // Then
        button.assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun GIVEN_onClick_WHEN_clicked_THEN_executed() = extension.runComposeTest {
        // Given
        val text = "text"
        var clickExecuted = false

        setThemedContent {
            PrimaryTextButton(
                text = text,
                onClick = { clickExecuted = true }
            )
        }

        // When
        val button = onNodeWithText("TEXT")
            .performClick()

        // Then
        button.assertIsDisplayed()
            .assertHasClickAction()

        assertTrue(clickExecuted)
    }

    @Test
    fun GIVEN_button_WHEN_added_THEN_matches_screenshot() = extension.runComposeTest {
        // Given
        val text = "text"

        setThemedScreenshotContent {
            PrimaryTextButton(
                text = text,
                onClick = { }
            )
        }

        // When
        val button = onNodeWithTag(testTagName)

        // Then
        button.assertIsDisplayed()
            .assertScreenshotMatches("atom", testTagName)
    }

    companion object {
        const val testTagName = "PrimaryTextButton"
    }
}
