/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import care.data4life.integration.app.test.compose.junit5.ComposeContext
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

fun ComposeContext.onWelcomePage() = WelcomePage(this)

class WelcomePage(
    composeContext: ComposeContext,
) : BasePage(composeContext) {
    private val screen: WelcomeScreen = WelcomeScreen(composeContext)

    fun doLogin(): LoginPage {
        screen.loginButton.performClick()

        return LoginPage(composeContext)
    }

    class WelcomeScreen(
        semanticsProvider: SemanticsNodeInteractionsProvider,
        private val testTagName: String = TEST_TAG_NAME,
    ) : ComposeScreen<WelcomeScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag(testTagName) }
    ) {
        val loginButton: KNode = child {
            hasTestTag("${this@WelcomeScreen.testTagName}$BUTTON_LOGIN")
        }

        private companion object {
            const val TEST_TAG_NAME = "WelcomeContent"

            const val BUTTON_LOGIN = "ButtonLogin"
        }
    }
}
