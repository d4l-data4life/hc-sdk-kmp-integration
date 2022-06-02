/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import care.data4life.integration.app.crud.BaseCrudTest.Companion
import care.data4life.integration.app.test.compose.BaseComposeTest
import care.data4life.sdk.Data4LifeClient
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.Callback
import care.data4life.sdk.listener.ResultListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class BaseSdkTest : BaseComposeTest() {

    protected lateinit var testSubject: Data4LifeClient

    @BeforeEach
    fun setup() {
        testSubject = Data4LifeClient.getInstance()

        setupBeforeEach()

        // old
        latch = CountDownLatch(1)
    }

    protected abstract fun setupBeforeEach()

    protected suspend fun <V> awaitListener(call: (listener: ResultListener<V>) -> Unit): Result<V> =
        suspendCoroutine { continuation ->
            call(
                object : ResultListener<V> {
                    override fun onSuccess(t: V) {
                        continuation.resume(Result.Success(t))
                    }

                    override fun onError(exception: D4LException) {
                        continuation.resume(Result.Failure(exception))
                    }
                }
            )
        }

    protected suspend fun awaitCallback(call: (callback: Callback) -> Unit): Result<Boolean> =
        suspendCoroutine { continuation ->
            call(
                object : Callback {
                    override fun onSuccess() {
                        continuation.resume(Result.Success(true))
                    }

                    override fun onError(exception: D4LException) {
                        continuation.resume(Result.Failure(exception))
                    }
                }
            )
        }

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Failure(val exception: Exception) : Result<Nothing>()
    }

    protected fun assertLoggedIn(expectedLoggedInState: Boolean) {
        var isLoggedIn = false
        BaseCrudTest.latch = CountDownLatch(1)
        testSubject.isUserLoggedIn(object : ResultListener<Boolean> {
            override fun onSuccess(loggedIn: Boolean) {
                isLoggedIn = loggedIn
                BaseCrudTest.latch.countDown()
            }

            override fun onError(exception: D4LException) {
                exception.printStackTrace()
                BaseCrudTest.latch.countDown()
            }
        })
        BaseCrudTest.latch.await(BaseCrudTest.TIMEOUT, TimeUnit.SECONDS)

        if (expectedLoggedInState) Assertions.assertTrue(isLoggedIn)
        else Assertions.assertFalse(isLoggedIn)
    }

    abstract inner class TestResultListener<V> : ResultListener<V> {
        override fun onError(exception: D4LException) {
            exception.printStackTrace()
            Companion.requestSuccessful = false
            Companion.latch.countDown()
        }
    }

    companion object {
        private val TIMEOUT = 10L

        @JvmStatic
        protected lateinit var latch: CountDownLatch
        private var requestSuccessful = true
    }
}
