/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.page

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until

abstract class BasePage {

    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())!!

    init {
        this.waitForPage()
    }

    abstract fun waitForPage()

    fun waitByResource(resourceName: String) {
        device.wait(Until.hasObject(By.res(resourceName)), TIMEOUT)
    }

    companion object {
        const val TIMEOUT = 1000 * 10L

        const val TIMEOUT_SHORT = 500 * 1L
    }
}
