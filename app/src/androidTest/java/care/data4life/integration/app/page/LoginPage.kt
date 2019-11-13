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

package care.data4life.integration.app.page

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import java.lang.Thread.sleep

class LoginPage : BasePage() {

    override fun waitForPage() {
        device.wait(Until.hasObject(By.pkg("com.android.chrome").depth(0)), TIMEOUT)
    }


    fun doLogin(email: String, password: String): HomePage {
        sleep(TIMEOUT_SHORT)

        val selector = UiSelector()

        // dismiss Chrome welcome screen
        val accept = device.findObject(selector.resourceId("com.android.chrome:id/terms_accept"))
        if (accept.exists()) {
            accept.click()
        }
        val noThanks = device.findObject(selector.resourceId("com.android.chrome:id/negative_button"))
        if (noThanks.exists()) {
            noThanks.click()
        }

        device.waitForIdle()
        waitByResource("root")
        device.waitForIdle()

        val loginTab = device.findObject(selector.className("android.view.View").textMatches("(Log in|Anmelden)"))
        if (loginTab.exists()) {
            loginTab.click()
            device.waitForIdle()
        }

        // accept cookies
        val acceptCookies = device.findObject(selector.className("android.widget.Button").textMatches("(Accept|Akzeptieren)"))
        if (acceptCookies.exists()) {
            acceptCookies.click()
            device.waitForIdle()
        }

        // close translate popup message
        val closeTranslatePopup = device.findObject(selector.resourceId("com.android.chrome:id/infobar_close_button"))
        if (closeTranslatePopup.exists()) {
            closeTranslatePopup.click()
            device.waitForIdle()
        }

        // scroll to bottom
        val wv = UiScrollable(selector.classNameMatches("android.webkit.WebView"))
        wv.scrollForward()
        wv.scrollToEnd(10)

        // enter credentials and press submit button
        val emailInput = device.findObject(selector.resourceId("d4l-email"))
        emailInput.text = email
        device.waitForIdle()

        val passwordInput = device.findObject(selector.resourceId("d4l-password"))
        passwordInput.text = password
        device.waitForIdle()

        val submit = device.findObject(selector.resourceId("d4l-button-submit-login"))
        submit.click()


        device.waitForIdle()
        device.wait(Until.hasObject(By.pkg("care.data4life.integration.app").depth(0)), TIMEOUT)

        return HomePage()
    }

    fun verifyNumber(countryCode: String, phoneNumber: String): HomePage {
        Thread.sleep(TIMEOUT_SHORT)

        val selector = UiSelector()

        // scroll to bottom
        val wv = UiScrollable(selector.classNameMatches("android.webkit.WebView"))
        wv.scrollForward()
        wv.scrollToEnd(10)

        // enter phone number and press next button
        val codeInput = device.findObject(selector.resourceId("d4l-code"))
        codeInput.text = countryCode
        device.waitForIdle()

        val phoneInput = device.findObject(selector.resourceId("d4l-phone-number"))
        phoneInput.text = phoneNumber
        device.waitForIdle()

        val submit = device.findObject(selector.resourceId("d4l-button-submit-login"))
        submit.click()

        device.waitForIdle()
        device.wait(Until.hasObject(By.pkg("care.data4life.integration.app").depth(0)), TIMEOUT)

        // send sms using Twilio to input number

        return HomePage()
    }


}
