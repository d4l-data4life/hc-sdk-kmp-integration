/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2019, HPS Gesundheitscloud gGmbH
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

package de.gesundheitscloud.sdk.integration.crud

import androidx.test.runner.AndroidJUnit4
import de.gesundheitscloud.fhir.stu3.model.*
import de.gesundheitscloud.fhir.stu3.util.FhirDateTimeParser
import de.gesundheitscloud.sdk.helpers.AttachmentBuilder
import de.gesundheitscloud.sdk.helpers.DocumentReferenceBuilder
import de.gesundheitscloud.sdk.helpers.getAttachments
import de.gesundheitscloud.sdk.helpers.getTitle
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(AndroidJUnit4::class)
class DocumentReferenceTest : BaseTest<DocumentReference>() {

    //region document properties
    private val attachmentTitle = "Brain MRI"
    private val createdDate = FhirDateTimeParser.parseDateTime("2013-04-03")
    private val contentType = "image/jpeg"
    private val data = byteArrayOf(0x25, 0x50, 0x44, 0x46, 0x2d)
    private val dataSizeBytes = data.size
    private val dataBase64 = "JVBERi0="
    private val dataHashBase64 = "6GUZUCson6BgvpmHylP3m4FZU4s="

    private val title = "Physical"
    private val indexed: FhirInstant = FhirDateTimeParser.parseInstant("2013-04-03T15:30:10+01:00")
    private val status = CodeSystems.DocumentReferenceStatus.CURRENT
    private val documentCode = "34108-1"
    private val documentDisplay = "Outpatient Note"
    private val documentSystem = "http://loinc.org"
    private val practiceSpecialityCode = "General Medicine"
    private val practiceSpecialityDisplay = "General Medicine"
    private val practiceSpecialitySystem = "http://www.ihe.net/xds/connectathon/practiceSettingCodes"
    //endregion

    override fun getModelClass(): Class<DocumentReference> {
        return DocumentReference::class.java
    }

    override fun getTestModel(): DocumentReference {
        val attachment = AttachmentBuilder.buildWith(attachmentTitle, createdDate, contentType, data)

        val author = Practitioner()
        val docTypeCoding = Coding().apply {
            code = documentCode
            display = documentDisplay
            system = documentSystem
        }
        val docType = CodeableConcept()
        docType.coding = listOf(docTypeCoding)

        val practiceSpecialityCoding = Coding().apply {
            code = practiceSpecialityCode
            display = practiceSpecialityDisplay
            system = practiceSpecialitySystem
        }
        val practiceSpeciality = CodeableConcept()
        practiceSpeciality.coding = listOf(practiceSpecialityCoding)

        return DocumentReferenceBuilder.buildWith(
                title,
                indexed,
                status,
                listOf(attachment),
                docType,
                author,
                practiceSpeciality)
    }

    override fun prepareModel(model: DocumentReference, method: Method, index: Int) {
        when (method) {
            Method.UPDATE -> {
                model.description = "New title"
                model.id = recordId
            }
            Method.BATCH_UPDATE -> {
                model.description = (if (index == 0) "doc1" else "doc2")
                model.id = recordId
            }
            else -> {
                fail("Unexpected case")
            }
        }
    }

    override fun assertModelExpectations(model: DocumentReference, method: Method, index: Int) {
        var assertRecordId = true
        if (method == Method.CREATE || method == Method.BATCH_CREATE || method == Method.FETCH_BY_TYPE) {
            assertRecordId = false
        }

        assertDocumentExpectations(model, assertRecordId)
        when (method) {
            Method.CREATE -> {
                assertEquals(title, model.getTitle())
                assertEquals(dataBase64, model.getAttachments()?.first()?.data)
            }
            Method.BATCH_CREATE -> {
                assertNotNull(model.id)
                assertEquals(title, model.getTitle())
                assertEquals(dataBase64, model.getAttachments()?.first()?.data)
            }
            Method.FETCH -> {
                assertEquals(title, model.getTitle())
                assertNull(model.getAttachments()?.first()?.data)
            }
            Method.FETCH_BY_ID -> {
                assertEquals(title, model.getTitle())
                assertNull(model.getAttachments()?.first()?.data)
            }
            Method.FETCH_BY_TYPE -> {
                assertNotNull(model.id)
                assertEquals(title, model.getTitle())
                assertNull(model.getAttachments()?.first()?.data)
            }
            Method.UPDATE -> {
                assertEquals("New title", model.getTitle())
                assertEquals(dataBase64, model.getAttachments()?.first()?.data)
            }
            Method.BATCH_UPDATE -> {
                assertEquals(if (index == 0) "doc1" else "doc2", model.getTitle())
                assertEquals(dataBase64, model.getAttachments()?.first()?.data)
            }
            Method.DOWNLOAD -> {
                assertEquals("doc2", model.getTitle())
                assertEquals(dataBase64, model.getAttachments()?.first()?.data)
            }
            Method.BATCH_DOWNLOAD -> {
                assertEquals("doc2", model.getTitle())
                assertEquals(dataBase64, model.getAttachments()?.first()?.data)
            }
            else -> {
                fail("Unexpected case")
            }
        }
    }

    private fun assertDocumentExpectations(doc: DocumentReference, assertRecordId: Boolean = true) {
        if (assertRecordId) Assert.assertEquals(recordId, doc.id)
        Assert.assertEquals(indexed, doc.indexed)
        Assert.assertEquals(status, doc.status)
        Assert.assertNotNull(doc.type)
        Assert.assertEquals(1, doc.type.coding?.size)
        Assert.assertEquals(documentCode, doc.type.coding?.first()?.code)
        Assert.assertEquals(documentDisplay, doc.type.coding?.first()?.display)
        Assert.assertEquals(documentSystem, doc.type.coding?.first()?.system)
        Assert.assertEquals(1, doc.context?.practiceSetting?.coding?.size)
        Assert.assertEquals(practiceSpecialityCode, doc.context?.practiceSetting?.coding?.first()?.code)
        Assert.assertEquals(practiceSpecialityDisplay, doc.context?.practiceSetting?.coding?.first()?.display)
        Assert.assertEquals(practiceSpecialitySystem, doc.context?.practiceSetting?.coding?.first()?.system)
        Assert.assertEquals(1, doc.getAttachments()?.size)
        assertAttachmentExpectations(doc.getAttachments()?.first()!!)
    }

    private fun assertAttachmentExpectations(attachment: Attachment) {
        Assert.assertNotNull(attachment.id)
        Assert.assertEquals(attachmentTitle, attachment.title)
        Assert.assertEquals(createdDate, attachment.creation)
        Assert.assertEquals(contentType, attachment.contentType)
        Assert.assertEquals(dataHashBase64, attachment.hash)
        Assert.assertEquals(dataSizeBytes, attachment.size)
    }
}
