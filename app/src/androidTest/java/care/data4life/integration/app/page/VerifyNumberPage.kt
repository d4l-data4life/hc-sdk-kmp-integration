package care.data4life.integration.app.page

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until

class VerifyNumberPage : BasePage() {

    override fun waitForPage() {
        device.wait(Until.hasObject(By.pkg("com.android.chrome").depth(0)), TIMEOUT)
    }


    fun verifyNumber(countryCode: String, phoneNumber: String): HomePage {
        Thread.sleep(TIMEOUT_SHORT)

        val selector = UiSelector()

        // scroll to bottom
        val wv = UiScrollable(selector.classNameMatches("android.webkit.WebView"))
        wv.scrollForward()
        wv.scrollToEnd(10)

        // enter phone number and press next button
        val codeInput = device.findObject(selector.resourceId("d4life-email"))
        codeInput.text = countryCode
        device.waitForIdle()

        val phoneInput = device.findObject(selector.resourceId("d4life-password"))
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
