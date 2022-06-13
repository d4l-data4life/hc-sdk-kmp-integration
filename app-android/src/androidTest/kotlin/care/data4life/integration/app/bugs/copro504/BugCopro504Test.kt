/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.bugs.copro504

import androidx.test.platform.app.InstrumentationRegistry
import care.data4life.fhir.r4.FhirR4Parser
import care.data4life.fhir.r4.model.DocumentReference
import care.data4life.integration.app.test.BaseSdkTest
import care.data4life.integration.app.data.wrapper.awaitCallback
import care.data4life.sdk.call.Fhir4Record
import care.data4life.sdk.helpers.r4.getAttachments
import care.data4life.sdk.util.Base64
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

import care.data4life.integration.app.data.wrapper.Result
import care.data4life.sdk.util.HashUtil
import kotlinx.coroutines.runBlocking

class BugCopro504Test : BaseSdkTest() {

    @Test
    fun validate() = extension.runComposeTest {
        // Given
        val fhir = loadAsString("$BASE_PATH/fhir-DocumentReference.json")
        val pdf = loadAsByteArray("$BASE_PATH/Tricky document.pdf")
        val pdfBase64 = Base64.encodeToString(pdf)
        val pdfSize = pdf.size
        val pdfHashBase64 = Base64.encodeToString(HashUtil.sha1(pdf))

        val documentReference = FhirR4Parser().toFhir(DocumentReference::class.java, fhir)
        documentReference.getAttachments()?.get(0)?.also {
            it.data = pdfBase64
            it.size = pdfSize
            it.hash = pdfHashBase64
        }

        // When
        login()
        assertLoggedIn(true)

        runBlocking {
            val result: Result<Fhir4Record<DocumentReference>> = awaitCallback { callback ->
                testSubject.fhir4.create(
                    documentReference,
                    emptyList(),
                    callback
                )
            }

            val abc = ""
        }

        // Cleanup
        logout()
    }

    private fun loadAsString(path: String): String {
        val assets = InstrumentationRegistry.getInstrumentation().context.assets
        return try {
            assets.open(path).bufferedReader().readText()
        } catch (exception: Throwable) {
            throw FileNotFoundException("File not found: $path")
        }
    }

    private fun loadAsByteArray(path: String): ByteArray {
        val assets = InstrumentationRegistry.getInstrumentation().context.assets
        return assets.open(path).readBytes()
    }

    companion object {
        const val BASE_PATH = "bugs/copro504"
    }
}
