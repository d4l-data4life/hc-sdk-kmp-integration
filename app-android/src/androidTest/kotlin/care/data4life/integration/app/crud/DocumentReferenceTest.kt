/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import care.data4life.fhir.stu3.model.Attachment
import care.data4life.fhir.stu3.model.CodeSystemDocumentReferenceStatus
import care.data4life.fhir.stu3.model.CodeableConcept
import care.data4life.fhir.stu3.model.Coding
import care.data4life.fhir.stu3.model.DocumentReference
import care.data4life.fhir.stu3.model.FhirInstant
import care.data4life.fhir.stu3.model.Practitioner
import care.data4life.fhir.stu3.util.FhirDateTimeParser
import care.data4life.sdk.helpers.stu3.AttachmentBuilder
import care.data4life.sdk.helpers.stu3.DocumentReferenceBuilder
import care.data4life.sdk.helpers.stu3.getAttachments
import care.data4life.sdk.helpers.stu3.getTitle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DocumentReferenceTest : BaseCrudTest<DocumentReference>() {

    //region document properties
    val attachmentTitle = "Brain MRI"
    val createdDate = FhirDateTimeParser.parseDateTime("2013-04-03")
    val contentType = "image/jpeg"
    val data = byteArrayOf(0x25, 0x50, 0x44, 0x46, 0x2d)
    val dataSizeBytes = data.size
    val dataBase64 = "JVBERi0="
    val dataHashBase64 = "6GUZUCson6BgvpmHylP3m4FZU4s="

    val title = "Physical"
    val indexed: FhirInstant = FhirDateTimeParser.parseInstant("2013-04-03T15:30:10+01:00")
    val status = CodeSystemDocumentReferenceStatus.CURRENT
    val documentCode = "34108-1"
    val documentDisplay = "Outpatient Note"
    val documentSystem = "http://loinc.org"
    val practiceSpecialityCode = "General Medicine"
    val practiceSpecialityDisplay = "General Medicine"
    val practiceSpecialitySystem = "http://www.ihe.net/xds/connectathon/practiceSettingCodes"
    //endregion

    private val NEW_TITLE = "New title"
    private val TITLE_1 = "doc1"
    private val TITLE_2 = "doc2"

    @Test
    fun testAll() = extension.runComposeTest {
        login()

        assertLoggedIn(true)

        runCrudSingleTests()
        runCrudBatchTests()

        logout()
    }

    override fun getTestClass(): Class<DocumentReference> {
        return DocumentReference::class.java
    }

    override fun getModel(
        method: Method,
        index: Int
    ): DocumentReference {
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

        val document = DocumentReferenceBuilder.buildWith(
            title,
            indexed,
            status,
            listOf(attachment),
            docType,
            author,
            practiceSpeciality
        )
        mutateModel(document, method, index)

        return document
    }

    private fun mutateModel(
        model: DocumentReference,
        method: Method,
        index: Int
    ) {
        when (method) {
            Method.UPDATE -> {
                model.description = NEW_TITLE
                model.id = recordId
            }
            Method.BATCH_UPDATE -> {
                model.description = (if (index == 0) TITLE_1 else TITLE_2)
                model.id = recordId
            }
            else -> {
                // ignore
            }
        }
    }

    override fun assertModelExpectations(
        model: DocumentReference,
        method: Method,
        index: Int
    ) {
        var assertRecordId = true
        var docTitle = title
        var docDataBase64: String? = dataBase64

        when (method) {
            Method.BATCH_CREATE -> {
                assertNotNull(model.id)
                assertRecordId = false
            }
            Method.FETCH -> {
                docDataBase64 = null
            }
            Method.FETCH_BY_ID -> {
                docDataBase64 = null
            }
            Method.FETCH_BY_TYPE -> {
                assertNotNull(model.id)
                assertRecordId = false
                docDataBase64 = null
            }
            Method.UPDATE -> {
                docTitle = NEW_TITLE
            }
            Method.BATCH_UPDATE -> {
                docTitle = if (index == 0) TITLE_1 else TITLE_2
            }
            Method.DOWNLOAD -> {
                docTitle = TITLE_2
            }
            Method.BATCH_DOWNLOAD -> {
                docTitle = TITLE_2
            }
            else -> {
                // ignore
            }
        }

        assertDocumentExpectations(model, assertRecordId, docTitle, docDataBase64)
    }

    private fun assertDocumentExpectations(
        doc: DocumentReference,
        assertRecordId: Boolean = true,
        docTitle: String,
        docDataBase64: String?
    ) {
        if (assertRecordId) assertEquals(recordId, doc.id)
        assertEquals(docTitle, doc.getTitle())
        assertEquals(indexed, doc.indexed)
        assertEquals(status, doc.status)
        assertNotNull(doc.type)
        assertEquals(1, doc.type.coding?.size)
        assertEquals(documentCode, doc.type.coding?.first()?.code)
        assertEquals(documentDisplay, doc.type.coding?.first()?.display)
        assertEquals(documentSystem, doc.type.coding?.first()?.system)
        assertEquals(1, doc.context?.practiceSetting?.coding?.size)
        assertEquals(practiceSpecialityCode, doc.context?.practiceSetting?.coding?.first()?.code)
        assertEquals(practiceSpecialityDisplay, doc.context?.practiceSetting?.coding?.first()?.display)
        assertEquals(practiceSpecialitySystem, doc.context?.practiceSetting?.coding?.first()?.system)
        assertEquals(1, doc.getAttachments()?.size)
        assertAttachmentExpectations(doc.getAttachments()?.first()!!, docDataBase64)
    }

    private fun assertAttachmentExpectations(
        attachment: Attachment,
        docDataBase64: String?
    ) {
        assertNotNull(attachment.id)
        assertEquals(attachmentTitle, attachment.title)
        assertEquals(createdDate, attachment.creation)
        assertEquals(contentType, attachment.contentType)
        assertEquals(dataHashBase64, attachment.hash)
        assertEquals(dataSizeBytes, attachment.size)
        assertEquals(docDataBase64, attachment.data)
    }
}
