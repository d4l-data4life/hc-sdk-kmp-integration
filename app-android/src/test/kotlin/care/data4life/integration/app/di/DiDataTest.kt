/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.di

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@Suppress("TestFunctionName")
class DiDataTest {

    @Test
    fun di_implements_contract() {
        assertInstanceOf(DiContract.Data::class.java, Di.data)
    }

    @Test
    fun GIVEN_d4lClientWrapper_WHEN_accessed_THEN_only_one_instance() {
        // Given
        val di = Di

        // When
        val wrapper1 = di.data.d4lClient
        val wrapper2 = di.data.d4lClient

        // Then
        assertTrue(wrapper1 === wrapper2)
        assertEquals(wrapper1, wrapper2)
    }

    @Test
    fun GIVEN_authService_WHEN_accessed_THEN_only_one_instance() {
        // Given
        val di = Di

        // When
        val auth1 = di.data.authService
        val auth2 = di.data.authService

        // Then
        assertTrue(auth1 === auth2)
        assertEquals(auth1, auth2)
    }
}
