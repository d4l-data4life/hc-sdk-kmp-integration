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

package de.gesundheitscloud.sdk.integration.crossdk

import androidx.test.runner.AndroidJUnit4
import de.gesundheitscloud.fhir.stu3.model.*
import de.gesundheitscloud.fhir.stu3.util.FhirDateTimeParser
import de.gesundheitscloud.sdk.HCException
import de.gesundheitscloud.sdk.helpers.*
import de.gesundheitscloud.sdk.listener.Callback
import de.gesundheitscloud.sdk.listener.ResultListener
import de.gesundheitscloud.sdk.model.DownloadResult
import de.gesundheitscloud.sdk.model.Record
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.threeten.bp.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CrossSDKTest : BaseTestLogin() {
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

    //region practitioner properties
    val TEXT = "Dr. Bruce Banner, Praxis fuer Allgemeinmedizin"
    val NAME = "Bruce"
    val SURNAME = "Banner"
    val PREFIX = "Dr."
    val SUFFIX = "MD"
    val STREET = "Walvisbaai 3"
    val POSTAL_CODE = "2333ZA"
    val CITY = "Den helder"
    val TELEPHONE = "+31715269111"
    val WEBSITE = "www.webpage.com"
    //endregion

    val RECREATE_ANDROID_DOC_REF = false // set to true when android DocumentReference needs to be recreated
    val TIMEOUT = 10L
    val ANDROID_ID = "android"
    val MAX_PAGE_SIZE = 1000
    var clientCallSuccessful = true
    lateinit var latch: CountDownLatch

    companion object {
        @JvmStatic
        lateinit var recordIds: MutableList<String>
    }

    var expectedClientIds = mutableSetOf(ANDROID_ID, "ios", "web")

    @Test
    fun fetchRecords_shouldFetchRecords() {
        clientCallSuccessful = true
        latch = CountDownLatch(1)
        lateinit var fetchedRecords: List<Record<DocumentReference>>

        client.fetchRecords(
                DocumentReference::class.java,
                null,
                LocalDate.now(),
                MAX_PAGE_SIZE, 0,
                object : ResultListener<List<Record<DocumentReference>>> {
                    override fun onSuccess(records: List<Record<DocumentReference>>) {
                        fetchedRecords = records
                        latch.countDown()
                    }

                    override fun onError(exception: HCException) {
                        clientCallSuccessful = false
                        latch.countDown()
                    }
                })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
        assertTrue("Fetch records failed", clientCallSuccessful)
        if (!RECREATE_ANDROID_DOC_REF) assertEquals(3, fetchedRecords.size)

        var androidRecordId: String? = null
        recordIds = fetchedRecords.map {
            if (it.fhirResource.getAdditionalIds()?.first()?.value?.equals(ANDROID_ID)!!) {
                androidRecordId = it.fhirResource.id!!
            }
            it.fhirResource.id!!
        }.toMutableList()

        if (RECREATE_ANDROID_DOC_REF) {
            androidRecordId?.let {
                recordIds.remove(it)
                deleteRecord(it)
            }
            createRecord()
            recordIds.add(createRecord().fhirResource.id!!)
        }
    }

    private fun deleteRecord(recordId: String) {
        clientCallSuccessful = true
        latch = CountDownLatch(1)

        client.deleteRecord(recordId, object : Callback {
            override fun onSuccess() {
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                clientCallSuccessful = false
                latch.countDown()
            }

        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
        assertTrue("Delete record failed", clientCallSuccessful)
    }

    private fun createRecord(): Record<DocumentReference> {
        clientCallSuccessful = true
        latch = CountDownLatch(1)
        lateinit var createdRecord: Record<DocumentReference>

        val doc = buildTestDocument().apply {
            addAdditionalId(ANDROID_ID)
        }

        client.createRecord(doc, object : ResultListener<Record<DocumentReference>> {
            override fun onSuccess(record: Record<DocumentReference>) {
                createdRecord = record
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                clientCallSuccessful = false
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
        assertTrue("Create record failed", clientCallSuccessful)
        return createdRecord
    }

    @Test
    fun t02_downloadRecords_shouldDownloadRecords() {
        clientCallSuccessful = true
        latch = CountDownLatch(1)
        lateinit var downloadResult: DownloadResult<DocumentReference>

        client.downloadRecords(recordIds, object : ResultListener<DownloadResult<DocumentReference>> {
            override fun onSuccess(result: DownloadResult<DocumentReference>) {
                downloadResult = result
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                clientCallSuccessful = false
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        assertTrue("Download records failed", clientCallSuccessful)
        assertTrue(downloadResult.failedDownloads.isEmpty())
        assertEquals(3, downloadResult.successfulDownloads.size)

        downloadResult.successfulDownloads.map {
            val clientId = it.fhirResource.getAdditionalIds()?.first()?.value
            assertTrue(expectedClientIds.remove(clientId))
            assertDocumentExpectations(it.fhirResource)
        }
        assertTrue(expectedClientIds.isEmpty())
    }

    private fun assertDocumentExpectations(doc: DocumentReference) {
        assertNotNull(doc.id)
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
        assertAttachmentExpectations(doc.getAttachments()?.first()!!)
        assertAuthorExpectations(doc.getPractitioner()!!)
    }

    private fun assertAttachmentExpectations(attachment: Attachment) {
        assertNotNull(attachment.id)
        assertEquals(attachmentTitle, attachment.title)
        assertEquals(createdDate, attachment.creation)
        assertEquals(contentType, attachment.contentType)
        assertEquals(dataHashBase64, attachment.hash)
        assertEquals(dataSizeBytes, attachment.size)
        assertEquals(dataBase64, attachment.data)
    }

    private fun assertAuthorExpectations(practitioner: Practitioner) {
        assertEquals(1, practitioner.name?.size)
        assertEquals(1, practitioner.name!![0].given?.size)
        assertEquals(NAME, practitioner.name!![0].given!![0])
        assertEquals(SURNAME, practitioner.name!![0].family)
        assertEquals(1, practitioner.name!![0].prefix?.size)
        assertEquals(PREFIX, practitioner.name!![0].prefix!![0])
        assertEquals(1, practitioner.name!![0].suffix?.size)
        assertEquals(SUFFIX, practitioner.name!![0].suffix!![0])
        assertEquals(1, practitioner.address?.size)
        assertEquals(1, practitioner.address!![0].line?.size)
        assertEquals(STREET, practitioner.address!![0].line!![0])
        assertEquals(POSTAL_CODE, practitioner.address!![0].postalCode)
        assertEquals(CITY, practitioner.address!![0].city)
        assertEquals(2, practitioner.telecom?.size)
        assertEquals(CodeSystems.ContactPointSystem.PHONE, practitioner.telecom!![0].system)
        assertEquals(TELEPHONE, practitioner.telecom!![0].value)
        assertEquals(CodeSystems.ContactPointSystem.URL, practitioner.telecom!![1].system)
        assertEquals(WEBSITE, practitioner.telecom!![1].value)
    }

    private fun buildTestDocument(): DocumentReference {
        val attachment = AttachmentBuilder.buildWith(
                attachmentTitle,
                createdDate,
                contentType,
                data)

        val author = PractitionerBuilder.buildWith(
                NAME,
                SURNAME,
                PREFIX,
                SUFFIX,
                STREET,
                POSTAL_CODE,
                CITY,
                TELEPHONE,
                WEBSITE)

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
}
