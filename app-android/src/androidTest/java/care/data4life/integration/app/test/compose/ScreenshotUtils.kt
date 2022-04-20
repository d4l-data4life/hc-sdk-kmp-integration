/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test.compose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream

/**
 * On device screenshot comparison against an expected screenshot.
 *
 * Expected screenshots must be available `androidTest/assets`.
 *
 * Screenshot dimensions must match, otherwise test will fail. Limit the surface to a fixed size.
 *
 * If `saveAsExpected` is enabled, current screenshot will be saved on device in `data/data/{package}/files`
 */
fun SemanticsNodeInteraction.assertScreenshotMatches(
    expectedFilePath: String,
    saveAsExpected: Boolean = false
) {
    val actualBitmap = captureToImage().asAndroidBitmap()
    val expectedBitmap = loadExpectedScreenshot(expectedFilePath)

    if (saveAsExpected) {
        saveExpectedScreenshot(expectedFilePath, actualBitmap)
    }

    if (expectedBitmap == null) {
        throw AssertionError("expected screenshot not present in assets folder: $expectedFilePath")
    } else {
        actualBitmap.compare(expectedBitmap)
    }
}

private fun loadExpectedScreenshot(path: String): Bitmap? {
    val assets = InstrumentationRegistry.getInstrumentation().context.assets
    return try {
        val file = assets.open(path)
        file.use { BitmapFactory.decodeStream(it) }
    } catch (exception: Throwable) {
        null
    }
}

private fun saveExpectedScreenshot(path: String, bitmap: Bitmap) {
    val outputPath = InstrumentationRegistry.getInstrumentation().targetContext.filesDir.canonicalPath
    val filePath = "$outputPath/$path"
    val folder = File(filePath.substringBeforeLast("/"))
    if (!folder.exists()) {
        folder.mkdirs()
    }
    FileOutputStream(filePath).use { output ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
    }
    println("Saved expected screenshot: $filePath")
}

private fun Bitmap.compare(other: Bitmap) {
    assert(this.width == other.width && this.height == other.height) {
        "dimensions must be equal: ${height}x${width} vs other ${other.height}x${other.width}"
    }

    for (column in 0 until height) {
        val row1 = this.readRow(column)
        val row2 = other.readRow(column)

        assert(row1.contentEquals(row2)) { "Bitmap row content is different" }
    }
}

private fun Bitmap.readRow(column: Int): IntArray {
    val row = IntArray(width)
    this.getPixels(row, 0, width, 0, column, width, 1)
    return row
}
