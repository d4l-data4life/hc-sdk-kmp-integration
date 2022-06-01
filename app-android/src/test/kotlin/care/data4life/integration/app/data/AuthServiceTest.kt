/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data

import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.data.DataContract.Wrapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@Suppress("TestFunctionName")
class AuthServiceTest {

    lateinit var client: Wrapper.D4LClient

    lateinit var service: Service.Auth

    @BeforeEach
    fun setup() {
        client = mockk()

        service = AuthService(client)
    }

    @Test
    fun implements_contract() {
        assertInstanceOf(Service.Auth::class.java, service)
    }

    @Test
    fun GIVEN_logged_in_WHEN_isAuthorized_called_THEN_return_true() {
        // Given
        coEvery { service.isAuthorized() } returns true

        // When
        val result = runBlocking { service.isAuthorized() }

        // Then
        assertTrue(result)
    }

    @Test
    fun GIVEN_logged_out_WHEN_isAuthorized_called_THEN_return_false() {
        // Given
        coEvery { service.isAuthorized() } returns false

        // When
        val result = runBlocking { service.isAuthorized() }

        // Then
        assertFalse(result)
    }
}
