/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.di

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

@Suppress("TestFunctionName")
class DiUiTest {

    @Test
    fun di_implements_contract() {
        assertInstanceOf(DiContract.Ui::class.java, Di.ui)
    }

    @Test
    fun GIVEN_welcomeViewModel_WHEN_accessed_THEN_always_new_instance() {
        // Given
        val di = Di

        // When
        val welcomeViewModel1 = di.ui.welcomeViewModel
        val welcomeViewModel2 = di.ui.welcomeViewModel

        // Then
        assertFalse(welcomeViewModel1 === welcomeViewModel2)
        assertNotEquals(welcomeViewModel1, welcomeViewModel2)
    }
}
