/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.flow

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.page.WelcomePage
import care.data4life.integration.app.test.TestConfigLoader
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginFlowTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun testLoginLogoutFlow() {
        val activity = rule.launchActivity(null)

        val user = TestConfigLoader.load().user

        WelcomePage()
            .isVisible()
            .openLoginPage() // LoginPage //FIXME login Page visibility check is missing
            .doLogin(user) // HomePage
            .isVisible()
            .doLogout() // WelcomeScreen
            .isVisible()

        activity.explicitFinish()
    }
}
