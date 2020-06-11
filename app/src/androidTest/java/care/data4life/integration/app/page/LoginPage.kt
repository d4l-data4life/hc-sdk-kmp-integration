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

import android.webkit.WebView
import android.widget.Button
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import care.data4life.integration.app.BuildConfig
import care.data4life.integration.app.testUtils.Auth2FAHelper
import care.data4life.integration.app.testUtils.User
import java.lang.Thread.sleep

class LoginPage : BasePage() {

    override fun waitForPage() {
        device.wait(Until.hasObject(By.pkg("com.android.chrome").depth(0)), TIMEOUT)
    }

    fun doLogin(user: User): HomePage {
        sleep(TIMEOUT_SHORT)

        // Chrome
        dismissChromeWelcomeScreen()

        // AuthApp
        dismissAuthAppCookie()

        // Page login/register
        scrollToBottom(2)
        clickButton(authAppButtonLogin, true)

        // Page enter email/password
        scrollToBottom(2)
        enterText(authAppInputEmail, user.email, true)
        enterText(authAppInputPassword, user.password, true)
        clickButton(authAppButtonSubmitLogin, true)

        // Chrome
        sleep(TIMEOUT_SHORT)
        dismissChromeSavePassword()

        // AuthApp

        // Page Phone number
        // FIXME ids missing so disabled it
//        scrollToBottom(2)
//        enterText(authAppInputPhoneCountryCode, user.phoneCountryCode, false)
//        enterText(authAppInputPhoneNumber, user.phoneLocalNumber, false)
//        clickButton(authAppButtonPhoneNumber, false)

        // Page 2FA
        scrollToBottom(2)
        val code = Auth2FAHelper().fetchCurrent2faCode(user.phoneNumber)
        enter2FA(code)
        unselectRememberDeviceCheckbox()
        clickButton(authAppButtonSmsCodeSubmit, true)

        // In case 2FA code was wrong, retry 3 times
        repeat(3) {
            resendCode(user.phoneNumber)
        }

        return HomePage()
    }


    private fun enter2FA(code: String) {
        when (BuildConfig.FLAVOR) {
            "development" -> enterText(authAppInputPinV2, code,true)
            else -> enterVerificationCodeV1(code)
        }
    }

    private fun doLoginV1(user: User) {
        // close notification about leaving app popup
        val closeNotifyPopup = device.findObject(UiSelector().resourceId("com.android.chrome:id/infobar_close_button"))
        sleep(TIMEOUT_SHORT)
        if (closeNotifyPopup.exists()) {
            closeNotifyPopup.click()
            device.waitForIdle()
        }
        // authorize access
        val authorizeAccess = device.findObject(UiSelector().className("android.widget.Button").textMatches("(ADD|HINZUFÜGEN)"))
        if (authorizeAccess.exists()) {
            authorizeAccess.click()
            device.waitForIdle()
        }

        // close translate popup message
        val closeTranslatePopup = device.findObject(UiSelector().resourceId("com.android.chrome:id/infobar_close_button"))
        if (closeTranslatePopup.exists()) {
            closeTranslatePopup.click()
            device.waitForIdle()
        }

        device.waitForIdle()
        device.wait(Until.hasObject(By.pkg("care.data4life.integration.app").depth(0)), TIMEOUT)
    }

    // Chrome
    private fun dismissChromeWelcomeScreen() {
        // dismiss Chrome welcome screen
        val accept = device.findObject(UiSelector().resourceId("com.android.chrome:id/terms_accept"))
        if (accept.exists()) {
            accept.click()
            device.waitForIdle()
        }
        val noThanks = device.findObject(UiSelector().resourceId("com.android.chrome:id/negative_button"))
        if (noThanks.exists()) {
            noThanks.click()
            device.waitForIdle()
        }
    }

    private fun dismissChromeSavePassword() {
        dismissChromeInfobar()
    }

    private fun dismissChromeInfobar() {
        val closeNotifyPopup = device.findObject(UiSelector().resourceId("com.android.chrome:id/infobar_close_button"))
        if (closeNotifyPopup.exists()) {
            closeNotifyPopup.click()
            device.waitForIdle()
        }
    }

    // Auth App

    // FIXME cookie consent needs a stable ID
    private fun dismissAuthAppCookie() {
        val acceptCookies = device.findObject(
                UiSelector().instance(0)
                        .className(Button::class.java)
                        .descriptionMatches("(Accept|Akzeptieren)")
        )
        acceptCookies.waitForExists(TIMEOUT_SHORT)
        if (acceptCookies.exists()) {
            acceptCookies.click()
            device.waitForIdle()
        }
    }

    private fun enterVerificationCodeV1(verificationCode: String) {
        for (x in 0 until 6) {
            val digit = device.findObject(UiSelector().resourceId(authAppInputPinV1.plus(x + 1)))
            digit.text = verificationCode[x].toString()
        }
    }

    private fun unselectRememberDeviceCheckbox() {
        val rememberCheckBox = device.findObject(UiSelector().resourceId("d4l-checkbox-remember"))
        if (rememberCheckBox.isChecked)
            rememberCheckBox.click()
    }

    private fun click() {
        clickButton(authAppButtonSmsCodeSubmit, true)
    }

    private fun resendCode(phoneNumber: String) {
        val dismissButton = device.findObject(UiSelector().className("android.widget.Button").textMatches("(DISMISS)"))
        if (dismissButton.exists()) {
            dismissButton.click()

            val resend = device.findObject(UiSelector().resourceId("d4l-button-resend-sms-code"))
            resend.click()

            sleep(TIMEOUT_SHORT)
            val code = Auth2FAHelper.fetchCurrent2faCode(phoneNumber)
            enterVerificationCodeV1(code)
        }

    }


    // Helper

    private fun scrollToBottom(maxSwipes: Int) {
        UiScrollable(UiSelector().className(WebView::class.java))
                .scrollToEnd(maxSwipes)
    }

    private fun clickButton(resourceId: String, required: Boolean?) {
        val button = device.findObject(UiSelector().instance(0).resourceId(resourceId))
        button.waitForExists(TIMEOUT_SHORT)
        if (button.exists()) {
            button.click()
            device.waitForIdle()
        } else {
            if (required != null && required) throw IllegalStateException("Button not found: $resourceId")
        }
    }

    private fun enterText(resourceId: String, text: String, required: Boolean?) {
        val input = device.findObject(UiSelector().instance(0).resourceId(resourceId))
        input.waitForExists(TIMEOUT_SHORT)
        if (input.exists()) {
            input.text = text
            device.waitForIdle()
        } else {
            if (required != null && required) throw IllegalStateException("Input not found: $resourceId")
        }
    }

    companion object {
        // Page register/login
        const val authAppButtonLogin = "d4l-button-login"

        // Page enter email/password
        const val authAppInputEmail = "d4l-email"
        const val authAppInputPassword = "d4l-password"
        const val authAppButtonSubmitLogin = "d4l-button-submit-login"

        // Page phone number
        const val authAppInputPhoneCountryCode = "d4l-code"
        const val authAppInputPhoneNumber = "d4l-phone-number"
        const val authAppButtonPhoneNumber = "d4l-button-submit-login"

        // Page 2FA
        const val authAppInputPinV1 = "d4l-pin-position-"
        const val authAppInputPinV2 = "d4l-pin"
        const val authAppButtonSmsCodeSubmit = "d4l-button-submit-sms-code"
    }
}
