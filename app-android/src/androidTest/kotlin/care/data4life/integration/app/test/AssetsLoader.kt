/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test

import androidx.test.platform.app.InstrumentationRegistry
import java.io.FileNotFoundException

fun loadAsset(path: String): ByteArray {
    val assets = InstrumentationRegistry.getInstrumentation().context.assets
    return try {
        assets.open(path).readBytes()
    } catch (exception: Throwable) {
        throw FileNotFoundException("File not found: $path")
    }
}
