/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data.wrapper

import care.data4life.integration.app.data.wrapper.Result.Success
import care.data4life.sdk.lang.D4LException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@Suppress("TestFunctionName")
class SdkCallbackWrapperKtTest {

    @Test
    fun GIVEN_boolean_WHEN_called_THEN_return_boolean() {
        // Given
        val expected = true

        // When
        val result: Result<Boolean> = runBlocking { awaitCallback { it.onSuccess(expected) } }

        // Then
        assertTrue((result as Success).data)
    }

    @Test
    fun GIVEN_string_WHEN_called_THEN_return_string() {
        // Given
        val expected = "success"

        // When
        val result: Result<String> = runBlocking { awaitCallback { it.onSuccess(expected) } }

        // Then
        assertEquals(expected, (result as Success).data)
    }

    @Test
    fun GIVEN_exception_WHEN_called_THEN_throw_exception() {
        // Given
        val exception = D4LException("exception")

        // When/Then
        Assertions.assertThrowsExactly(
            D4LException::class.java
        ) { runBlocking { awaitCallback<Boolean> { it.onError(exception) } } }
    }
}
