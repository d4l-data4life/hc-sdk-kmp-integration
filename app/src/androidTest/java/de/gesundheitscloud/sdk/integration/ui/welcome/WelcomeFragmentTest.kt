/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2018, HPS Gesundheitscloud gGmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.gesundheitscloud.sdk.integration.ui.welcome

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import de.gesundheitscloud.sdk.integration.MainActivity
import de.gesundheitscloud.sdk.integration.page.HomePage
import de.gesundheitscloud.sdk.integration.page.WelcomePage
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@Ignore
class WelcomeFragmentTest {

    @Rule
    @JvmField
    val rule = IntentsTestRule(MainActivity::class.java)


    private val welcomeScreen = WelcomePage.WelcomeScreen()
    private val homeScreen = HomePage.HomeScreen()

    @Ignore
    @Test
    fun testLoginFlow() {


        welcomeScreen {
            loginButton {
                click()
            }

            val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            val selector = UiSelector()

            // dismiss Chrome welcome screen
            val accept = device.findObject(selector.textMatches("ACCEPT & CONTINUE"))
            if (accept.exists()) {
                accept.click()
            }
            val noThanks = device.findObject(selector.textMatches("NO THANKS"))
            if (noThanks.exists()) {
                noThanks.click()
            }

            // scroll to bottom
            val wv = UiScrollable(selector.classNameMatches("android.webkit.WebView"))
            wv.scrollForward()
            wv.scrollToEnd(10)

            // enter credentials and press submit button
            val email = device.findObject(selector.resourceId("emailInput"))
            email.legacySetText("l57719@nwytg.com")
            device.pressBack()
            val password = device.findObject(selector.resourceId("passwordInput"))
            password.legacySetText("password1")
            device.pressBack()

            val submit = device.findObject(selector.resourceId("loginButton"))
            submit.click()
        }

        homeScreen {
            logoutButton.click()
        }

    }
}
