/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.test.compose.createAndroidComposeExtension
import care.data4life.integration.app.ui.theme.IntegrationTheme
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertTrue

@Suppress("TestFunctionName")
class PrimaryTextButtonTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    @Test
    fun GIVEN_text_WHEN_displayed_THEN_its_uppercase() = extension.runComposeTest {
        // Given
        val text = "text"

        setContent {
            IntegrationTheme {
                PrimaryTextButton(
                    text = text,
                    onClick = { /*none*/ }
                )
            }
        }

        // When/Then
        onNodeWithText("TEXT")
            .assertIsDisplayed()
    }

    @Test
    fun GIVEN_onClick_WHEN_clicked_THEN_executed() = extension.runComposeTest {
        // Given
        val text = "text"
        var clickExecuted = false

        setContent {
            IntegrationTheme {
                PrimaryTextButton(
                    text = text,
                    onClick = { clickExecuted = true }
                )
            }
        }

        // When
        onNodeWithText("TEXT")
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        // Then
        assertTrue(clickExecuted)
    }
}
