/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import care.data4life.integration.app.test.compose.junit5.ComposeContentContext
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

fun ComposeContentContext.onHomePage() = HomePage(this)

class HomePage(
    composeContext: ComposeContentContext
) : BasePage(composeContext) {

    private val screen = HomeScreen(composeContext)

    fun doLogout(): WelcomePage {
        screen.logoutButton.performClick()

        return WelcomePage(composeContext)
    }

    class HomeScreen(
        semanticsProvider: SemanticsNodeInteractionsProvider,
        private val testTagName: String = TEST_TAG_NAME
    ) : ComposeScreen<HomeScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag(testTagName) }
    ) {
        val logoutButton: KNode = child {
            hasTestTag("${this@HomeScreen.testTagName}$BUTTON_LOGOUT")
        }

        private companion object {
            const val TEST_TAG_NAME = "HomeContent"

            const val BUTTON_LOGOUT = "ButtonLogout"
        }
    }
}
