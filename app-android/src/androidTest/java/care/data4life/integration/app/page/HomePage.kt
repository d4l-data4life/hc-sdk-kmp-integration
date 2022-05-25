/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import care.data4life.integration.app.R
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton

class HomePage : BasePage() {

    private val screen = HomeScreen()

    override fun waitForPage() {
        waitByResource("care.data4life.integration.app:id/home_constraint")
    }

    fun doLogout(): WelcomePage {
        screen.logoutButton { click() }

        return WelcomePage()
    }

    fun isVisible(): HomePage {
        screen.root { isDisplayed() }

        return this
    }

    class HomeScreen : Screen<HomeScreen>() {
        val root = KView {
            // withId(R.id.home_constraint)
        }

        val logoutButton = KButton {
            // withId(R.id.home_logout_button)
        }
    }
}
