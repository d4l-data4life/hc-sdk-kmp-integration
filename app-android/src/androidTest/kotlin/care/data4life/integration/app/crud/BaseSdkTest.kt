/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import care.data4life.integration.app.crud.BaseSdkTest.Result.Failure
import care.data4life.integration.app.crud.BaseSdkTest.Result.Success
import care.data4life.integration.app.page.HomePage
import care.data4life.integration.app.page.onWelcomePage
import care.data4life.integration.app.test.TestConfigLoader
import care.data4life.integration.app.test.compose.BaseComposeTest
import care.data4life.integration.app.test.compose.junit5.ComposeContentContext
import care.data4life.sdk.Data4LifeClient
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.Callback
import care.data4life.sdk.listener.ResultListener
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class BaseSdkTest : BaseComposeTest() {

    private lateinit var homePage: HomePage

    protected lateinit var testSubject: Data4LifeClient

    @BeforeEach
    fun setup() {
        testSubject = Data4LifeClient.getInstance()
    }

    protected fun ComposeContentContext.login() {
        val user = TestConfigLoader.load().user
        homePage = onWelcomePage()
            .doLogin()
            .doLogin(user)

        waitForIdle()
    }

    protected fun ComposeContentContext.logout() {
        homePage
            .doLogout()

        waitForIdle()
    }

    protected fun assertLoggedIn(expectedLoggedInState: Boolean) = runBlocking {
        val result: Result<Boolean> = awaitListener { listener ->
            testSubject.isUserLoggedIn(listener)
        }

        when (result) {
            is Success -> assertEquals(
                expectedLoggedInState,
                result.data,
                "Logged in state is not as expected."
            )
            is Failure -> fail("Failed to check logged in state: ${result.exception.message}")
        }
    }

    protected suspend fun <V> awaitListener(call: (listener: ResultListener<V>) -> Unit): Result<V> =
        suspendCoroutine { continuation ->
            call(
                object : ResultListener<V> {
                    override fun onSuccess(t: V) {
                        continuation.resume(Success(t))
                    }

                    override fun onError(exception: D4LException) {
                        continuation.resume(Failure(exception))
                    }
                }
            )
        }

    protected suspend fun awaitCallback(call: (callback: Callback) -> Unit): Result<Boolean> =
        suspendCoroutine { continuation ->
            call(
                object : Callback {
                    override fun onSuccess() {
                        continuation.resume(Success(true))
                    }

                    override fun onError(exception: D4LException) {
                        continuation.resume(Failure(exception))
                    }
                }
            )
        }

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Failure(val exception: Exception) : Result<Nothing>()
    }

    abstract inner class TestResultListener<V> : ResultListener<V> {
        override fun onError(exception: D4LException) {
            exception.printStackTrace()
        }
    }
}
