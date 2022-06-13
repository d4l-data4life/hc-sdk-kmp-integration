/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test

import care.data4life.integration.app.data.wrapper.Result
import care.data4life.integration.app.data.wrapper.awaitLegacyListener
import care.data4life.integration.app.page.HomePage
import care.data4life.integration.app.page.onWelcomePage
import care.data4life.integration.app.test.compose.junit5.ComposeContentContext
import care.data4life.sdk.Data4LifeClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach

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
        val result: Result<Boolean> = awaitLegacyListener { listener ->
            testSubject.isUserLoggedIn(listener)
        }

        when (result) {
            is Result.Success -> assertEquals(
                expectedLoggedInState,
                result.data,
                "Logged in state is not as expected."
            )
            is Result.Failure -> fail("Failed to check logged in state: ${result.exception.message}")
        }
    }
}
