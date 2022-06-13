/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud.fhir3

import care.data4life.fhir.stu3.model.Attachment
import care.data4life.fhir.stu3.model.CodeSystemDocumentReferenceStatus
import care.data4life.fhir.stu3.model.CodeableConcept
import care.data4life.fhir.stu3.model.Coding
import care.data4life.fhir.stu3.model.DocumentReference
import care.data4life.fhir.stu3.model.FhirInstant
import care.data4life.fhir.stu3.model.Practitioner
import care.data4life.fhir.stu3.util.FhirDateTimeParser
import care.data4life.sdk.call.CallContract.Record
import care.data4life.sdk.helpers.stu3.AttachmentBuilder
import care.data4life.sdk.helpers.stu3.DocumentReferenceBuilder
import care.data4life.sdk.helpers.stu3.getAttachments
import care.data4life.sdk.helpers.stu3.getTitle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DocumentReferenceTest : BaseFhir3CrudTest<DocumentReference>() {

    @Test
    fun testAll() = extension.runComposeTest {
        login()

        assertLoggedIn(true)

        runBlocking {
            runTestSuite(1)

            // runTestSuite(10)
        }

        logout()
    }

    override fun assertCreateRecord(expected: DocumentReference, actual: Record<DocumentReference>) {
        assertDocumentReference(expected, actual.resource, true)
    }

    override fun assertFetchRecord(expected: DocumentReference, actual: Record<DocumentReference>) {
        assertDocumentReference(expected, actual.resource, false)
    }

    override fun assertFetchByType(expected: DocumentReference, actual: Record<DocumentReference>) {
        assertDocumentReference(expected, actual.resource, false)
    }

    override fun assertUpdate(expected: DocumentReference, actual: Record<DocumentReference>) {
        assertDocumentReference(expected, actual.resource, false)
    }

    override fun assertDownload(expected: DocumentReference, actual: Record<DocumentReference>) {
        assertDocumentReference(expected, actual.resource, true)
    }

    override fun getTestClass(): Class<DocumentReference> {
        return DocumentReference::class.java
    }

    override fun getId(record: Record<DocumentReference>): String? {
        return record.resource.id
    }

    override fun generateItem(): DocumentReference {
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
            practiceSpeciality
        )
    }

    override fun mutateItem(item: DocumentReference): DocumentReference {
        return item.also {
            it.description = it.description + " - iteration:"
        }
    }

    private fun assertDocumentReference(expected: DocumentReference, actual: DocumentReference, isDownload: Boolean) {
        assertNotNull(actual.id)
        assertEquals(expected.id, actual.id)

        assertEquals(expected.getTitle(), actual.getTitle())
        assertEquals(expected.indexed, actual.indexed)
        assertEquals(expected.status, actual.status)

        assertNotNull(actual.type)
        assertEquals(expected.type.coding?.size, actual.type.coding?.size)
        assertEquals(expected.type.coding?.first()?.code, actual.type.coding?.first()?.code)
        assertEquals(expected.type.coding?.first()?.system, actual.type.coding?.first()?.system)

        assertEquals(expected.context?.practiceSetting?.coding?.size, actual.context?.practiceSetting?.coding?.size)
        assertEquals(
            expected.context?.practiceSetting?.coding?.first()?.code,
            actual.context?.practiceSetting?.coding?.first()?.code
        )
        assertEquals(
            expected.context?.practiceSetting?.coding?.first()?.display,
            actual.context?.practiceSetting?.coding?.first()?.display
        )
        assertEquals(
            expected.context?.practiceSetting?.coding?.first()?.system,
            actual.context?.practiceSetting?.coding?.first()?.system
        )

        assertAttachments(expected.getAttachments(), actual.getAttachments(), isDownload)
    }

    private fun assertAttachments(expected: List<Attachment>?, actual: List<Attachment>?, isDownload: Boolean) {
        assertEquals(expected?.size, actual?.size)
        expected?.mapIndexed { index, attachment ->
            actual?.get(index)?.let { assertAttachment(attachment, it, isDownload) }
        }
    }

    private fun assertAttachment(expected: Attachment, actual: Attachment, isDownload: Boolean) {
        assertNotNull(actual.id)
        assertEquals(expected.id, actual.id)
        assertEquals(expected.title, actual.title)
        assertEquals(expected.creation, actual.creation)
        assertEquals(expected.contentType, actual.contentType)
        assertEquals(expected.hash, actual.hash)
        assertEquals(expected.size, actual.size)
        if (isDownload) {
            assertEquals(expected.data, actual.data)
        }
    }

    companion object {
        val attachmentTitle = "Brain MRI"
        val createdDate = FhirDateTimeParser.parseDateTime("2013-04-03")
        val contentType = "application/pdf"
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
    }
}
