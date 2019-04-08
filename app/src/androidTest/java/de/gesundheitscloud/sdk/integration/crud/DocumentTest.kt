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

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen
import de.gesundheitscloud.fhir.stu3.model.*
import de.gesundheitscloud.fhir.stu3.util.FhirDateTimeParser
import de.gesundheitscloud.sdk.HCException
import de.gesundheitscloud.sdk.HealthCloudAndroid
import de.gesundheitscloud.sdk.helpers.AttachmentBuilder
import de.gesundheitscloud.sdk.helpers.DocumentReferenceBuilder
import de.gesundheitscloud.sdk.helpers.getAttachments
import de.gesundheitscloud.sdk.helpers.getTitle
import de.gesundheitscloud.sdk.integration.MainActivity
import de.gesundheitscloud.sdk.integration.page.HomePage
import de.gesundheitscloud.sdk.integration.page.WelcomePage
import de.gesundheitscloud.sdk.integration.testUtils.NetworkUtil
import de.gesundheitscloud.sdk.integration.testUtils.deleteAllRecords
import de.gesundheitscloud.sdk.listener.Callback
import de.gesundheitscloud.sdk.listener.ResultListener
import de.gesundheitscloud.sdk.model.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.threeten.bp.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //test order is important for successful completion!
class DocumentTest {
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

    companion object {
        private var isNetConnected = NetworkUtil.isOnline()
        private val rule = ActivityTestRule(MainActivity::class.java, false, false)
        private lateinit var activity: MainActivity
        private lateinit var homePage: HomePage
        @JvmStatic
        private lateinit var recordId: String
        @JvmStatic
        private var recordIds = mutableListOf<String>()
        @JvmStatic
        private lateinit var client: HealthCloudAndroid  //SUT

        @BeforeClass
        @JvmStatic
        fun classSetup() {
            assertTrue("Internet connection required", isNetConnected)

            activity = rule.launchActivity(null)
            AndroidThreeTen.init(activity.application)

            homePage = WelcomePage()
                    .isVisible()
                    .openLoginPage()
                    .doLogin("wolf.montwe+staging2@gesundheitscloud.de", "asdfgh1!")
                    .isVisible()

            client = HealthCloudAndroid.getInstance()
            client.deleteAllRecords(DocumentReference::class.java)
        }

        @AfterClass
        @JvmStatic
        fun classCleanUp() {
            if(!isNetConnected) return

            client.deleteAllRecords(DocumentReference::class.java)

            homePage
                    .doLogout()
                    .isVisible()

            activity.explicitFinish()
        }
    }

    private val TIMEOUT = 10L
    private lateinit var document : DocumentReference
    private lateinit var latch : CountDownLatch


    @Before
    fun testSetup() {
        document = buildTestDocument()
        latch = CountDownLatch(1)
    }

    @Test
    fun t01_createDocument_shouldReturn_createdDocument() {
        //when
        client.createRecord(document, object: ResultListener<Record<DocumentReference>> {
            override fun onSuccess(record: Record<DocumentReference>) {

                //then
                val created = record.fhirResource
                assertNotNull(recordId)
                recordId = created.id!!
                assertDocumentExpectations(created)
                assertEquals(title, created.getTitle())
                assertEquals(dataBase64, created.getAttachments()?.first()?.data)

                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t02_createDocuments_shouldReturn_createdDocuments() {
        //given
        val doc1 = buildTestDocument()
        val doc2 = buildTestDocument()

        //when
        client.createRecords(listOf(doc1, doc2), object: ResultListener<CreateResult<DocumentReference>> {
            override fun onSuccess(result: CreateResult<DocumentReference>) {

                //then
                assertEquals(2, result.successfulOperations.size)
                assertTrue(result.failedOperations.isEmpty())

                result.successfulOperations.map{
                    val created = it.fhirResource
                    assertDocumentExpectations(created, false)
                    assertNotNull(created.id)
                    assertEquals(title, created.getTitle())
                    assertEquals(dataBase64, created.getAttachments()?.first()?.data)
                    recordIds.add(created.id!!)
                }

                latch.countDown()
            }
            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t03_countDocuments_shouldReturn_documentCount() {
        //when
        client.countRecords(DocumentReference::class.java, object: ResultListener<Int> {
            override fun onSuccess(documentsCount: Int) {

                //then
                assertEquals(3, documentsCount)
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t04_countRecords_shouldReturn_recordCount() {
        //when
        client.countRecords(null, object: ResultListener<Int> {
            override fun onSuccess(documentsCount: Int) {

                //then
                assertTrue(documentsCount >= 3)
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t05_fetchDocument_shouldReturn_fetchedDocument() {
        //given
        assertNotNull("recordId expected", recordId)

        //when
        client.fetchRecord(recordId, object: ResultListener<Record<DocumentReference>> {
            override fun onSuccess(record: Record<DocumentReference>) {

                //then
                val fetched = record.fhirResource
                assertDocumentExpectations(fetched)
                assertEquals(title, fetched.getTitle())
                assertNull(fetched.getAttachments()?.first()?.data)

                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t06_fetchDocuments_shouldReturn_fetchedDocuments() {
        //given
        assertNotNull("recordId expected", recordId)

        //when
        client.fetchRecords(listOf(recordId, recordId), object: ResultListener<FetchResult<DocumentReference>> {
            override fun onSuccess(result: FetchResult<DocumentReference>) {

                //then
                assertEquals(2, result.successfulFetches.size)
                assertTrue(result.failedFetches.isEmpty())
                result.successfulFetches.map {
                    val fetched = it.fhirResource
                    assertDocumentExpectations(fetched)
                    assertEquals(title, fetched.getTitle())
                    assertNull(fetched.getAttachments()?.first()?.data)
                }

                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t07_fetchDocumentsByType_shouldReturn_fetchedDocuments() {
        //given
        assertNotNull("recordId expected", recordId)

        //when
        client.fetchRecords(
                DocumentReference::class.java,
                LocalDate.now().minusYears(10),
                LocalDate.now(),
                1000,
                0,
                object: ResultListener<List<Record<DocumentReference>>> {
                    override fun onSuccess(records: List<Record<DocumentReference>>) {

                        //then
                        assertEquals(3, records.size)
                        records.map {
                            val fetched = it.fhirResource
                            assertDocumentExpectations(fetched, false)
                            assertNotNull(fetched.id)
                            assertEquals(title, fetched.getTitle())
                            assertNull(fetched.getAttachments()?.first()?.data)
                        }
                        latch.countDown()
                    }

                    override fun onError(exception: HCException) {
                        exception.printStackTrace()
                        fail("Unexpected")
                    }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t08_updateDocument_shouldReturn_updatedDocument() {
        //given
        assertNotNull("recordId expected", recordId)
        document.description = "New title"
        document.id = recordId

        //when
        client.updateRecord(document, object: ResultListener<Record<DocumentReference>> {
            override fun onSuccess(record: Record<DocumentReference>) {

                //then
                val updated = record.fhirResource
                assertDocumentExpectations(updated)
                assertEquals("New title", updated.getTitle())
                assertEquals(dataBase64, updated.getAttachments()?.first()?.data)

                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t09_updateDocuments_shouldReturn_updatedDocuments() {
        //given
        assertNotNull("recordId expected", recordId)
        val doc1 = buildTestDocument().apply {
            description = "doc1"
            id = recordId
        }
        val doc2 = buildTestDocument().apply {
            description = "doc2"
            id = recordId
        }

        //when
        client.updateRecords(listOf(doc1, doc2), object: ResultListener<UpdateResult<DocumentReference>> {
            override fun onSuccess(result: UpdateResult<DocumentReference>) {

                //then
                assertTrue(result.failedUpdates.isEmpty())
                assertEquals(2, result.successfulUpdates.size)
                var cnt = 0
                result.successfulUpdates.map {
                    val updated = it.fhirResource
                    assertDocumentExpectations(updated)
                    assertEquals(if(cnt++ == 0) "doc1" else "doc2", updated.getTitle())
                    assertEquals(dataBase64, updated.getAttachments()?.first()?.data)
                }

                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t10_downloadDocument_shouldReturn_downloadedDocument() {
        //given
        assertNotNull("recordId expected", recordId)

        //when
        client.downloadRecord(recordId, object: ResultListener<Record<DocumentReference>> {
            override fun onSuccess(record: Record<DocumentReference>) {

                //then
                val downloaded = record.fhirResource
                assertDocumentExpectations(downloaded)
                assertEquals("doc2", downloaded.getTitle())
                assertEquals(dataBase64, downloaded.getAttachments()?.first()?.data)

                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t11_downloadDocuments_shouldReturn_downloadedDocuments() {
        //given
        assertNotNull("recordId expected", recordId)

        //when
        client.downloadRecords(listOf(recordId, recordId), object: ResultListener<DownloadResult<DocumentReference>> {
            override fun onSuccess(result: DownloadResult<DocumentReference>) {

                //then
                assertTrue(result.failedDownloads.isEmpty())
                assertEquals(2, result.successfulDownloads.size)
                result.successfulDownloads.map {
                    val downloaded = it.fhirResource
                    assertDocumentExpectations(downloaded)
                    assertEquals("doc2", downloaded.getTitle())
                    assertEquals(dataBase64, downloaded.getAttachments()?.first()?.data)
                }

                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected")
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t12_deleteDocument_shouldDeleteDocument() {
        //given
        assertNotNull("recordId expected", recordId)

        //when
        client.deleteRecord(recordId, object: Callback {
            override fun onSuccess() {

                //then
                assertTrue(true)
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected case")
            }

        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }

    @Test
    fun t13_deleteDocuments_shouldDeleteDocuments() {
        //given
        assertEquals("recordIds expected", 2, recordIds.size)

        //when
        client.deleteRecords(recordIds, object: ResultListener<DeleteResult> {
            override fun onSuccess(result: DeleteResult) {

                //then
                assertTrue(result.failedDeletes.isEmpty())
                assertEquals(2, result.successfulDeletes.size)
                for(i in 0 .. 1) assertEquals(recordIds[i], result.successfulDeletes[i])
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                fail("Unexpected case")
            }

        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)
    }


    private fun assertDocumentExpectations(doc: DocumentReference, assertRecordId: Boolean = true) {
        if(assertRecordId) assertEquals(recordId, doc.id)
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
    }

    private fun assertAttachmentExpectations(attachment: Attachment) {
        assertNotNull(attachment.id)
        assertEquals(attachmentTitle, attachment.title)
        assertEquals(createdDate, attachment.creation)
        assertEquals(contentType, attachment.contentType)
        assertEquals(dataHashBase64, attachment.hash)
        assertEquals(dataSizeBytes, attachment.size)
    }

    private fun buildTestDocument(): DocumentReference {
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
}
