/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import care.data4life.fhir.stu3.model.DomainResource
import care.data4life.integration.app.page.HomePage
import care.data4life.sdk.Data4LifeClient
import care.data4life.sdk.SdkContract
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.Callback
import care.data4life.sdk.model.CreateResult
import care.data4life.sdk.model.DeleteResult
import care.data4life.sdk.model.DownloadResult
import care.data4life.sdk.model.FetchResult
import care.data4life.sdk.model.Meta
import care.data4life.sdk.model.Record
import care.data4life.sdk.model.UpdateResult
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.threeten.bp.LocalDateTime

@TestMethodOrder(MethodOrderer.MethodName::class)
abstract class BaseCrudTest<T : DomainResource> : BaseSdkTest() {

    protected lateinit var homePage: HomePage

    protected var recordId: String = ""
    protected var recordIds: MutableList<String> = mutableListOf()

    override fun setupBeforeEach() {
        // TODO needed?
    }

    @AfterEach
    fun tearDown() {
        // TODO needed?
        // assertLoggedIn(false)
    }

    @Test
    fun t01_createRecord_shouldReturn_createdRecord() {
        // Given
        lateinit var record: Record<T>

        // When
        testSubject.createRecord(
            getModel(Method.CREATE),
            object : TestResultListener<Record<T>>() {
                override fun onSuccess(r: Record<T>) {
                    record = r
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Create record failed")
        assertRecordExpectations(record)
        assertNotNull(record.fhirResource.id)
        recordId = record.fhirResource.id!!
        assertModelExpectations(record.fhirResource, Method.CREATE)
    }

    @Test
    fun t02_createRecords_shouldReturn_createdRecords() {
        lateinit var createResult: CreateResult<T>

        // given
        val model1 = getModel(Method.BATCH_CREATE)
        val model2 = getModel(Method.BATCH_CREATE)

        // When
        testSubject.createRecords(
            listOf(model1, model2),
            object : TestResultListener<CreateResult<T>>() {
                override fun onSuccess(result: CreateResult<T>) {
                    createResult = result
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Create records failed")
        assertEquals(2, createResult.successfulOperations.size)
        assertTrue(createResult.failedOperations.isEmpty())

        createResult.successfulOperations.map {
            assertRecordExpectations(it)
            assertNotNull(it.fhirResource.id)
            recordIds.add(it.fhirResource.id!!)
            assertModelExpectations(it.fhirResource, Method.BATCH_CREATE)
        }
    }

    @Test
    fun t03_countRecords_shouldReturn_recordCount() {
        var modelCount: Int = -1

        // When
        testSubject.countRecords(
            getTestClass(),
            object : TestResultListener<Int>() {
                override fun onSuccess(count: Int) {
                    modelCount = count
                }
            }
        )

        // Then
        assertNotEquals(-1, modelCount, "Count records failed")
        assertEquals(3, modelCount)
    }

    @Test
    fun t04_countAllRecords_shouldReturn_recordCount() {
        var modelCount: Int = -1

        // When
        testSubject.countRecords(
            null,
            object : TestResultListener<Int>() {
                override fun onSuccess(count: Int) {
                    modelCount = count
                }
            }
        )

        // Then
        assertNotEquals(-1, modelCount, "Count all records failed")
        assertEquals(3, modelCount)
    }

    @Test
    fun t05_fetchRecord_shouldReturn_fetchedRecord() {
        // given
        assertNotNull("recordId expected", recordId)
        lateinit var record: Record<T>

        // When
        testSubject.fetchRecord(
            recordId,
            object : TestResultListener<Record<T>>() {
                override fun onSuccess(r: Record<T>) {
                    record = r
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Fetch record failed")
        assertRecordExpectations(record)
        assertModelExpectations(record.fhirResource, Method.FETCH)
    }

    @Test
    fun t06_fetchRecords_shouldReturn_fetchedRecords() {
        // given
        assertNotNull("recordId expected", recordId)
        lateinit var fetchResult: FetchResult<T>

        // When
        testSubject.fetchRecords(
            listOf(recordId, recordId),
            object : TestResultListener<FetchResult<T>>() {
                override fun onSuccess(result: FetchResult<T>) {
                    fetchResult = result
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Fetch records failed")
        assertEquals(2, fetchResult.successfulFetches.size)
        assertTrue(fetchResult.failedFetches.isEmpty())
        fetchResult.successfulFetches.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.FETCH_BY_ID)
        }
    }

    @Test
    fun t07_fetchRecordsByType_shouldReturn_fetchedRecords() {
        // given
        assertNotNull("recordId expected", recordId)
        lateinit var fetchedRecords: List<Record<T>>

        // When
        testSubject.fetchRecords(
            getTestClass(),
            null,
            SdkContract.UpdateDateTimeRange(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
            ),
            false,
            1000,
            0,
            object : TestResultListener<List<Record<T>>>() {
                override fun onSuccess(records: List<Record<T>>) {
                    fetchedRecords = records
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Fetch records by type failed")
        assertEquals(3, fetchedRecords.size)
        fetchedRecords.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.FETCH_BY_TYPE)
        }
    }

    @Test
    fun t08_updateRecord_shouldReturn_updatedRecord() {
        // given
        assertNotNull("recordId expected", recordId)
        lateinit var updatedRecord: Record<T>

        // When
        testSubject.updateRecord(
            getModel(Method.UPDATE),
            object : TestResultListener<Record<T>>() {
                override fun onSuccess(record: Record<T>) {
                    updatedRecord = record
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Update record failed")
        assertRecordExpectations(updatedRecord)
        assertModelExpectations(updatedRecord.fhirResource, Method.UPDATE)
    }

    @Test
    fun t09_updateRecords_shouldReturn_updatedRecords() {
        // given
        assertNotNull("recordId expected", recordId)
        lateinit var updateResult: UpdateResult<T>

        val model1 = getModel(Method.BATCH_UPDATE, 0)
        val model2 = getModel(Method.BATCH_UPDATE, 1)

        // When
        testSubject.updateRecords(
            listOf(model1, model2),
            object : TestResultListener<UpdateResult<T>>() {
                override fun onSuccess(result: UpdateResult<T>) {
                    updateResult = result
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Update records failed")
        assertEquals(2, updateResult.successfulUpdates.size)
        assertTrue(updateResult.failedUpdates.isEmpty())

        var cnt = 0
        updateResult.successfulUpdates.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.BATCH_UPDATE, cnt++)
        }
    }

    @Test
    fun t10_downloadRecord_shouldReturn_downloadedRecord() {
        // given
        assertNotNull("recordId expected", recordId)
        lateinit var downloadedRecord: Record<T>

        // When
        testSubject.downloadRecord(
            recordId,
            object : TestResultListener<Record<T>>() {
                override fun onSuccess(record: Record<T>) {
                    downloadedRecord = record
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Download record failed")
        assertRecordExpectations(downloadedRecord)
        assertModelExpectations(downloadedRecord.fhirResource, Method.DOWNLOAD)
    }

    @Test
    fun t11_downloadRecords_shouldReturn_downloadedRecord() {
        // given
        assertNotNull("recordId expected", recordId)
        lateinit var downloadResult: DownloadResult<T>

        // When
        testSubject.downloadRecords(
            listOf(recordId, recordId),
            object : TestResultListener<DownloadResult<T>>() {
                override fun onSuccess(result: DownloadResult<T>) {
                    downloadResult = result
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Download records failed")
        assertEquals(2, downloadResult.successfulDownloads.size)
        assertTrue(downloadResult.failedDownloads.isEmpty())
        downloadResult.successfulDownloads.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.BATCH_DOWNLOAD)
        }
    }

    @Test
    fun t12_deleteRecord_shouldDeleteRecord() {
        // given
        assertNotNull("recordId expected", recordId)

        // When
        testSubject.deleteRecord(
            recordId,
            object : Callback {
                override fun onSuccess() {
                }

                override fun onError(exception: D4LException) {
                    exception.printStackTrace()
                    requestSuccessful = false
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Delete record failed")
    }

    @Test
    fun t13_deleteRecords_shouldDeleteRecords() {
        // given
        assertEquals(2, recordIds.size, "recordIds expected")
        lateinit var deleteResult: DeleteResult

        // When
        testSubject.deleteRecords(
            recordIds,
            object : TestResultListener<DeleteResult>() {
                override fun onSuccess(result: DeleteResult) {
                    deleteResult = result
                }
            }
        )

        // Then
        assertTrue(requestSuccessful, "Delete records failed")
        assertEquals(2, deleteResult.successfulDeletes.size)
        assertTrue(deleteResult.failedDeletes.isEmpty())
        for (i in 0..1) assertEquals(recordIds[i], deleteResult.successfulDeletes[i])
    }

    abstract fun getTestClass(): Class<T>

    abstract fun getModel(method: Method, index: Int = -1): T

    abstract fun assertModelExpectations(
        model: T,
        method: Method,
        index: Int = -1
    )

    protected fun assertRecordExpectations(record: Record<T>) {
        assertNotNull(record.fhirResource)
        assertNotNull(record.fhirResource.id)
        assertMetaExpectations(record.meta as Meta?)
    }

    private fun assertMetaExpectations(meta: Meta?) {
        assertNotNull(meta)
        assertNotNull(meta?.createdDate)
        assertNotNull(meta?.updatedDate)
    }

    enum class Method {
        CREATE, BATCH_CREATE,
        FETCH, FETCH_BY_ID, FETCH_BY_TYPE,
        UPDATE, BATCH_UPDATE,
        DOWNLOAD, BATCH_DOWNLOAD,
        DELETE, BATCH_DELETE,
        COUNT
    }

    companion object {
        private var requestSuccessful = true
    }
}
