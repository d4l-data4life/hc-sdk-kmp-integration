/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.data.wrapper

import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.ResultListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> callSuspendResultListenerWrapper(
    block: (ResultListener<T>) -> Unit
): T = suspendCoroutine { continuation ->
    block(object : ResultListener<T> {
        override fun onError(exception: D4LException) {
            continuation.resumeWithException(exception)
        }

        override fun onSuccess(t: T) {
            continuation.resume(t)
        }
    })
}
