/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import android.webkit.WebView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import care.data4life.integration.app.test.Auth2FAHelper
import care.data4life.integration.app.test.BuildConfig
import care.data4life.integration.app.test.User
import care.data4life.integration.app.test.compose.junit5.ComposeContentContext
import java.util.Locale

fun ComposeContentContext.onLoginPage() = LoginPage(this)

class LoginPage(
    composeContext: ComposeContentContext
) : BasePage(composeContext) {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())!!

    init {
        waitForBrowser()
    }

    private fun waitForBrowser() {
        device.wait(Until.hasObject(By.pkg(chromePackageName).depth(0)), TIMEOUT_LONG)
    }

    private fun hasBrowser(): Boolean {
        return device.hasObject(By.pkg(chromePackageName).depth(0))
    }

    fun doLogin(user: User): HomePage {
        handleChromePage()
        handleLoginRegisterPage()
        handleAuthPage(user)

        if (!hasBrowser()) {
            return HomePage(composeContext)
        }

        handlePhoneNumberPage(user)
        handle2faPage(user)
        handleDataKeyPage(user)

        // Chrome
        dismissChromeInfoBar()

        if (!hasBrowser()) {
            return HomePage(composeContext)
        } else {
            throw IllegalStateException("Browser should be closed now")
        }
    }

    // Pages

    private fun handleChromePage() {
        dismissChromeWelcomeScreen()
    }

    private fun handleLoginRegisterPage() {
        dismissAuthAppCookie()
        clickButton(authAppRegisterButtonLogin, true)
    }

    private fun handleAuthPage(user: User) {
        enterText(authAppAuthInputEmail, user.email, true)
        enterText(authAppAuthInputPassword, user.password, true)
        clickButton(authAppAuthButtonSubmitLogin, true)
    }

    private fun handlePhoneNumberPage(user: User) {
        dismissChromeSavePassword()

        if (hasResource(authAppNumberInputPhoneNumber)) {
            enterText(authAppNumberInputPhoneCountryCode, user.phoneCountryCode, false)
            enterText(authAppNumberInputPhoneNumber, user.phoneLocalNumber, false)
            clickButton(authAppNumberButtonPhoneNumber, false)
            clickButton(authAppNumberButtonConfirm, false)
        }
    }

    private fun handle2faPage(user: User) {
        if (hasResource(authApp2faInputPin)) {
            for (index in 0 until max2faRetries) {
                val message = Auth2FAHelper.fetchCurrent2faCode(user.phoneNumber)
                if (message != null) {
                    val code = Auth2FAHelper.extractVerificationCode(message)
                    if (code != null) {
                        enterText(authApp2faInputPin, code, true)
                    }
                }

                if (index == 0) {
                    unselectCheckbox(authApp2faCheckboxRememberDevice)
                }

                clickButton(authApp2faButtonSmsCodeSubmit, true)

                val dismissButton = findResource(classNameButton, authApp2faButtonDismissRegex)
                if (dismissButton.exists()) {
                    dismissButton.click()

                    val resend = device.findObject(UiSelector().resourceId(authApp2faButtonResendSms))
                    resend.click()
                } else {
                    break
                }
            }
        }
    }

    private fun handleDataKeyPage(user: User) {
        if (hasResource(authAppDataKeyTextArea)) {
            enterText(
                authAppDataKeyTextArea,
                user.getS4hDataKeyForEnvironment(BuildConfig.FLAVOR.lowercase(Locale.ENGLISH))
            )
            clickButton(authAppDataKeyNextButton, false)
        }
    }

    // Chrome
    private fun dismissChromeWelcomeScreen() {
        // dismiss Chrome welcome screen
        val accept = device.findObject(UiSelector().resourceId(chromeAcceptTerms))
        accept.waitForExists(TIMEOUT_SHORT)
        if (accept.exists()) {
            accept.click()
            device.waitForIdle()
        }
        val noThanks = device.findObject(UiSelector().resourceId(chromeNegativeButton))
        noThanks.waitForExists(TIMEOUT_SHORT)
        if (noThanks.exists()) {
            noThanks.click()
            device.waitForIdle()
        }
    }

    private fun dismissChromeSavePassword() {
        dismissChromeInfoBar()
    }

    private fun dismissChromeInfoBar() {
        val closeNotifyPopup = device.findObject(UiSelector().resourceId(chromeInfoBarCloseButton))
        closeNotifyPopup.waitForExists(TIMEOUT_SHORT)
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
                .className(classNameButton)
                .descriptionMatches(authAppCookieButtonAcceptRegex)
        )
        acceptCookies.waitForExists(TIMEOUT_SHORT)
        if (acceptCookies.exists()) {
            acceptCookies.click()
            device.waitForIdle()
        }
    }

    // Helper
    private fun scrollToBottom(maxSwipes: Int) {
        UiScrollable(UiSelector().className(WebView::class.java))
            .scrollToEnd(maxSwipes)
    }

    private fun scrollToTop(maxSwipes: Int) {
        UiScrollable(UiSelector().className(WebView::class.java))
            .scrollToBeginning(maxSwipes)
    }

    private fun findResource(resourceId: String): UiObject {
        val resource = device.findObject(UiSelector().instance(0).resourceId(resourceId))
        resource.waitForExists(TIMEOUT_SHORT)
        return resource
    }

    private fun findResource(className: String, regex: String): UiObject {
        val resource = device.findObject(UiSelector().className(className).textMatches(regex))
        resource.waitForExists(TIMEOUT_SHORT)
        return resource
    }

    private fun hasResource(resourceId: String): Boolean {
        val resource = findResource(resourceId)
        resource.waitForExists(TIMEOUT_SHORT)
        return resource.exists()
    }

    private fun clickButton(resourceId: String, required: Boolean = false) {
        val button = findResource(resourceId)
        button.waitForExists(TIMEOUT_SHORT)
        if (button.exists()) {
            button.click()
            device.waitForIdle()
        } else {
            if (required) throw IllegalStateException("Button not found: $resourceId")
        }
    }

    private fun enterText(resourceId: String, text: String, required: Boolean = false) {
        val input = findResource(resourceId)
        input.waitForExists(TIMEOUT_SHORT)
        if (input.exists()) {
            input.text = text
            device.waitForIdle()
        } else {
            if (required) throw IllegalStateException("Input not found: $resourceId")
        }
    }

    private fun unselectCheckbox(resourceId: String, required: Boolean = false) {
        val checkbox = findResource(resourceId)
        if (checkbox.exists()) {
            checkbox.click()
        } else {
            if (required) throw IllegalStateException("Checkbox not found: $resourceId")
        }
    }

    companion object {
        // Chrome
        const val chromePackageName = "com.android.chrome"
        const val chromeAcceptTerms = "com.android.chrome:id/terms_accept"
        const val chromeNegativeButton = "com.android.chrome:id/negative_button"
        const val chromeInfoBarCloseButton = "com.android.chrome:id/infobar_close_button"

        // AuthApp Cookie
        const val authAppCookieButtonAcceptRegex = "(Accept|ACCEPT|Akzeptieren)"

        // Page register/login
        const val authAppRegisterButtonLogin = "d4l-button-login"

        // Page enter email/password
        const val authAppAuthInputEmail = "d4l-email"
        const val authAppAuthInputPassword = "d4l-password"
        const val authAppAuthButtonSubmitLogin = "d4l-button-submit-login"

        // Page phone number
        const val authAppNumberInputPhoneCountryCode = "d4l-code"
        const val authAppNumberInputPhoneNumber = "d4l-phone-number"
        const val authAppNumberButtonPhoneNumber = "d4l-button-submit-login"
        const val authAppNumberButtonConfirm = "d4l-button-confirm"

        // Page 2FA
        const val authApp2faInputPin = "d4l-pin"
        const val authApp2faCheckboxRememberDevice = "d4l-checkbox-remember"
        const val authApp2faButtonSmsCodeSubmit = "d4l-button-submit-sms-code"
        const val authApp2faButtonResendSms = "d4l-button-resend-sms-code"
        const val authApp2faButtonDismissRegex = "(DISMISS)"

        // Page DataKey
        const val authAppDataKeyTextArea = "main-key"
        const val authAppDataKeyNextButton = "d4l-button-submit-sms-code"

        // Types
        const val classNameButton = "android.widget.Button"

        const val max2faRetries = 3
    }
}
