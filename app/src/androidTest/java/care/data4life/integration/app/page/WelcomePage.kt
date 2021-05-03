/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import care.data4life.integration.app.R
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton

class WelcomePage : BasePage() {

    private val screen = WelcomeScreen()

    override fun waitForPage() {
        waitByResource("care.data4life.integration.app:id/welcome_constraint")
    }

    fun openLoginPage(): LoginPage {
        screen.loginButton { click() }

        return LoginPage()
    }

    fun isVisible(): WelcomePage {
        screen.root { isDisplayed() }

        return this
    }

    class WelcomeScreen : Screen<WelcomeScreen>() {
        val root = KView { withId(R.id.welcome_constraint) }

        val loginButton = KButton { withId(R.id.welcome_login_button) }
    }
}
