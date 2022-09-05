/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test.compose

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.test.platform.app.InstrumentationRegistry
import java.io.FileNotFoundException

/**
 * On device screenshot comparison against an expected screenshot.
 *
 * Expected screenshots must be available `androidTest/assets`.
 *
 * Screenshot dimensions must match, otherwise test will fail. Limit the surface to a fixed size.
 *
 * If `saveAsExpected` is enabled, current screenshot will be saved on device in `downloads`
 */
fun SemanticsNodeInteraction.assertScreenshotMatches(
    folderPath: String,
    fileName: String,
    saveAsExpected: Boolean = true
) {
    val actualBitmap = captureToImage().asAndroidBitmap()
    val screenShotName = "$fileName-${actualBitmap.width}x${actualBitmap.height}.png"
    val screenShotPath = "$folderPath/$screenShotName"

    if (saveAsExpected) {
        saveExpectedScreenshotInDownloads(screenShotPath, actualBitmap)
    }

    val expectedBitmap = loadExpectedScreenshot(screenShotPath)
    if (expectedBitmap == null) {
        throw AssertionError("expected screenshot not present in assets folder: $screenShotPath")
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
        throw FileNotFoundException("File not found: $path")
    }
}

private fun saveExpectedScreenshotInDownloads(name: String, bitmap: Bitmap) {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "screenshot/$name")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val resolver = InstrumentationRegistry.getInstrumentation().targetContext.contentResolver

    val uri: Uri? = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        runCatching {
            resolver.openOutputStream(uri)?.use { output ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            }
        }.getOrElse {
            uri.let {
                resolver.delete(uri, null, null)
            }
        }
    }
}

private fun Bitmap.compare(other: Bitmap) {
    assert(this.width == other.width && this.height == other.height) {
        "dimensions must be equal: ${height}x$width vs other ${other.height}x${other.width}"
    }

    for (column in 0 until height) {
        val row1 = this.readRow(column)
        val row2 = other.readRow(column)

        assert(row1.contentEquals(row2)) { "Screenshot row content is different" }
    }
}

private fun Bitmap.readRow(column: Int): IntArray {
    val row = IntArray(width)
    this.getPixels(row, 0, width, 0, column, width, 1)
    return row
}
