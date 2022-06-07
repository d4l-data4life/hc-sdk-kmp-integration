/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data.wrapper

import care.data4life.sdk.call.Callback
import care.data4life.sdk.listener.Callback as Fhir3Callback
import care.data4life.sdk.listener.ResultListener as Fhir3ResultListener
import care.data4life.sdk.lang.D4LException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> awaitCallback(call: (callback: Callback<T>) -> Unit): Result<T> =
    suspendCoroutine { continuation ->
        call(
            object : Callback<T> {
                override fun onSuccess(result: T) {
                    continuation.resume(Result.Success(result))
                }

                override fun onError(exception: D4LException) {
                    continuation.resume(Result.Failure(exception))
                }
            }
        )
    }

suspend fun awaitLegacyCallback(call: (callback: Fhir3Callback) -> Unit): Result<Boolean> =
    suspendCoroutine { continuation ->
        call(
            object : Fhir3Callback {
                override fun onSuccess() {
                    continuation.resume(Result.Success(true))
                }

                override fun onError(exception: D4LException) {
                    continuation.resume(Result.Failure(exception))
                }
            }
        )
    }

suspend fun <V> awaitLegacyListener(call: (listener: Fhir3ResultListener<V>) -> Unit): Result<V> =
    suspendCoroutine { continuation ->
        call(
            object : Fhir3ResultListener<V> {
                override fun onSuccess(t: V) {
                    continuation.resume(Result.Success(t))
                }

                override fun onError(exception: D4LException) {
                    continuation.resume(Result.Failure(exception))
                }
            }
        )
    }

suspend fun <T> callResultListener(
    call: (Fhir3ResultListener<T>) -> Unit
): T = suspendCoroutine { continuation ->
    call(object : Fhir3ResultListener<T> {
        override fun onError(exception: D4LException) {
            continuation.resumeWithException(exception)
        }

        override fun onSuccess(t: T) {
            continuation.resume(t)
        }
    })
}
