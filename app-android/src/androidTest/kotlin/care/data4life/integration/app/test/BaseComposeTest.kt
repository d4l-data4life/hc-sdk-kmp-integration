/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test

import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.test.compose.junit5.createAndroidComposeExtension
import org.junit.jupiter.api.extension.RegisterExtension

abstract class BaseComposeTest {

    @JvmField
    @RegisterExtension
    val extension = createAndroidComposeExtension<MainActivity>()
}
