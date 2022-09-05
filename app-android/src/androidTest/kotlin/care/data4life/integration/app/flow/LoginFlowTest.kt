/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.flow

import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.page.onWelcomePage
import care.data4life.integration.app.test.TestConfigLoader
import care.data4life.integration.app.test.compose.junit5.createAndroidComposeExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class LoginFlowTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()

    @Test
    fun testLoginLogoutFlow() = extension.runComposeTest {
        val user = TestConfigLoader.load().user

        onWelcomePage()
            .doLogin()
            .doLogin(user)
            .doLogout()
    }
}
