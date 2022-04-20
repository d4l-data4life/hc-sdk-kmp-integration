/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.common.atom

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.test.compose.createAndroidComposeExtension
import care.data4life.integration.app.test.compose.setThemedContent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class LogoKtTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    @Test
    fun GIVEN_logo_WHEN_added_THEN_is_displayed() = extension.runComposeTest {
        // Given
        setThemedContent {
            Logo()
        }

        // When
        val logo = onNodeWithTag("Logo")

        // Then
        logo.assertIsDisplayed()
    }
}
