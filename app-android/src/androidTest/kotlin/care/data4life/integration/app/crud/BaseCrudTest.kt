/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import care.data4life.fhir.stu3.model.DomainResource
import care.data4life.integration.app.crud.BaseCrudTest.Method.BATCH_CREATE
import care.data4life.integration.app.crud.BaseCrudTest.Method.CREATE
import care.data4life.integration.app.crud.BaseCrudTest.Method.UPDATE
import care.data4life.integration.app.crud.RecordAssertions.assertRecordExpectations
import care.data4life.integration.app.data.wrapper.awaitLegacyListener
import care.data4life.integration.app.data.wrapper.Result
import care.data4life.integration.app.data.wrapper.awaitLegacyCallback
import care.data4life.sdk.SdkContract
import care.data4life.sdk.model.CreateResult
import care.data4life.sdk.model.DeleteResult
import care.data4life.sdk.model.DownloadResult
import care.data4life.sdk.model.FetchResult
import care.data4life.sdk.model.Record
import care.data4life.sdk.model.UpdateResult
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

abstract class BaseCrudTest<T : DomainResource> : BaseSdkTest() {

    protected lateinit var recordId: String
    protected lateinit var recordIds: MutableList<String>

    protected fun runCrudSingleTests() = runBlocking {
        cleanAccount()

        recordId = ""

        test_single_01_createRecord_shouldReturn_createdRecord()
        test_single_02_countRecords_shouldReturn_recordCount()
        test_single_03_fetchRecord_shouldReturn_fetchedRecord()
        test_single_04_fetchRecordsByType_shouldReturn_fetchedRecords()
        test_single_05_updateRecord_shouldReturn_updatedRecord()
    }

    protected fun runCrudBatchTests() = runBlocking {
        cleanAccount()

        recordIds = mutableListOf()

        test_batch_01_createRecords_shouldReturn_createdRecords()
        test_batch_02_countRecords_shouldReturn_recordCount()
        test_batch_03_fetchRecords_shouldReturn_fetchedRecords()
        test_batch_04_fetchRecordsByType_shouldReturn_fetchedRecords()
        test_batch_05_updateRecords_shouldReturn_updatedRecords()
    }

    private suspend fun cleanAccount() {
        val result: Result<List<Record<T>>> = awaitLegacyListener { listener ->
            testSubject.fetchRecords(
                getTestClass(),
                SdkContract.CreationDateRange(
                    LocalDate.now().minusYears(1),
                    LocalDate.now()
                ),
                SdkContract.UpdateDateTimeRange(
                    LocalDateTime.now().minusYears(1),
                    LocalDateTime.now()
                ),
                false,
                1000,
                0,
                listener
            )
        }

        if (result is Result.Success) {
            awaitLegacyListener<DeleteResult> { listener ->
                testSubject.deleteRecords(result.data.map { it.fhirResource.id }, listener)
            }
        }
    }

    // region crud single
    private suspend fun test_single_01_createRecord_shouldReturn_createdRecord() {
        // Given
        val data = getModel(CREATE)

        // When
        val result: Result<Record<T>> = awaitLegacyListener { listener ->
            testSubject.createRecord(data, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val record = result.data
                assertRecordExpectations(record)
                val resource = record.fhirResource
                assertNotNull(resource.id)
                recordId = resource.id!!
                assertModelExpectations(resource, CREATE)
            }
            is Result.Failure -> {
                fail("Create record failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_single_02_countRecords_shouldReturn_recordCount() {
        // Given

        // When
        val result: Result<Int> = awaitLegacyListener { listener ->
            testSubject.countRecords(getTestClass(), listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val count = result.data
                assertEquals(1, count)
            }
            is Result.Failure -> {
                fail("Count records failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_single_03_fetchRecord_shouldReturn_fetchedRecord() {
        // Given

        // When
        val result: Result<Record<T>> = awaitLegacyListener { listener ->
            testSubject.fetchRecord(recordId, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val record = result.data
                assertRecordExpectations(record)
                assertModelExpectations(record.fhirResource, Method.FETCH)
            }
            is Result.Failure -> {
                fail("Fetch record failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_single_04_fetchRecordsByType_shouldReturn_fetchedRecords() {
        // Given

        // When
        val result: Result<List<Record<T>>> = awaitLegacyListener { listener ->
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
                listener
            )
        }

        // Then
        when (result) {
            is Result.Success -> {
                val fetchResult = result.data
                assertEquals(1, fetchResult.size)
                fetchResult.map { record ->
                    assertRecordExpectations(record)
                    assertModelExpectations(record.fhirResource, Method.FETCH_BY_TYPE)
                }
            }
            is Result.Failure -> {
                fail("Fetch records by type failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_single_05_updateRecord_shouldReturn_updatedRecord() {
        // Given
        val data = getModel(UPDATE)

        // When
        val result: Result<Record<T>> = awaitLegacyListener { listener ->
            testSubject.updateRecord(data, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val record = result.data
                assertRecordExpectations(record)
                assertModelExpectations(record.fhirResource, UPDATE)
            }
            is Result.Failure -> {
                fail("Update record failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_singel_06_downloadRecord_shouldReturn_downloadedRecord() {
        // Given
        assertNotNull("recordId expected", recordId)
        lateinit var downloadedRecord: Record<T>

        // When
        val result = awaitLegacyListener<Record<T>> { listener ->
            testSubject.downloadRecord(recordId, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val record = result.data
                assertRecordExpectations(record)
                assertModelExpectations(record.fhirResource, Method.DOWNLOAD)
            }
            is Result.Failure -> {
                fail("Download record failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_single_07_deleteRecord_shouldDeleteRecord() {
        // Given

        // When
        val result = awaitLegacyCallback { callback ->
            testSubject.deleteRecord(recordId, callback)
        }

        // Then
        when (result) {
            is Result.Success -> {
                assertTrue(result.data, "Delete record failed")
            }
            is Result.Failure -> {
                fail("Delete record failed: ${result.exception.message}")
            }
        }
    }

    // endregion

    // region crud batch
    private suspend fun test_batch_01_createRecords_shouldReturn_createdRecords() {
        // Given
        val model1 = getModel(BATCH_CREATE)
        val model2 = getModel(BATCH_CREATE)
        val data = listOf(model1, model2)

        // When
        val result: Result<CreateResult<T>> = awaitLegacyListener { listener ->
            testSubject.createRecords(data, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val createResult = result.data
                assertEquals(2, createResult.successfulOperations.size)
                assertTrue(createResult.failedOperations.isEmpty())
                createResult.successfulOperations.map { record ->
                    assertRecordExpectations(record)
                    val resource = record.fhirResource
                    assertNotNull(resource.id)
                    recordIds.add(resource.id!!)
                    assertModelExpectations(resource, BATCH_CREATE)
                }
            }
            is Result.Failure -> {
                fail("Create records failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_batch_02_countRecords_shouldReturn_recordCount() {
        // Given

        // When
        val result: Result<Int> = awaitLegacyListener { listener ->
            testSubject.countRecords(getTestClass(), listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val count = result.data
                assertEquals(2, count)
            }
            is Result.Failure -> {
                fail("Count records failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_batch_03_fetchRecords_shouldReturn_fetchedRecords() {
        // Given

        // When
        val result: Result<FetchResult<T>> = awaitLegacyListener { listener ->
            testSubject.fetchRecords(recordIds, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val fetchResult = result.data
                assertEquals(2, fetchResult.successfulFetches.size)
                assertTrue(fetchResult.failedFetches.isEmpty())
                fetchResult.successfulFetches.map { record ->
                    assertRecordExpectations(record)
                    assertModelExpectations(record.fhirResource, Method.FETCH_BY_ID)
                }
            }
            is Result.Failure -> {
                fail("Fetch records failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_batch_04_fetchRecordsByType_shouldReturn_fetchedRecords() {
        // Given

        // When
        val result: Result<List<Record<T>>> = awaitLegacyListener { listener ->
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
                listener
            )
        }

        // Then
        when (result) {
            is Result.Success -> {
                val fetchResult = result.data
                assertEquals(2, fetchResult.size)
                fetchResult.map { record ->
                    assertRecordExpectations(record)
                    assertModelExpectations(record.fhirResource, Method.FETCH_BY_TYPE)
                }
            }
            is Result.Failure -> {
                fail("Fetch records by type failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_batch_05_updateRecords_shouldReturn_updatedRecords() {
        // Given
        val model1 = getModel(Method.BATCH_UPDATE, 0)
        val model2 = getModel(Method.BATCH_UPDATE, 1)
        val data = listOf(model1, model2)

        // When
        val result = awaitLegacyListener<UpdateResult<T>> { listener ->
            testSubject.updateRecords(data, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val updateResult = result.data
                assertEquals(2, updateResult.successfulUpdates.size)
                assertTrue(updateResult.failedUpdates.isEmpty())

                updateResult.successfulUpdates.mapIndexed { index, record ->
                    assertRecordExpectations(record)
                    assertModelExpectations(record.fhirResource, Method.BATCH_UPDATE, index)
                }
            }
            is Result.Failure -> {
                fail("Update records failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_batch_06_downloadRecords_shouldReturn_downloadedRecord() {
        // Given

        // When
        val result = awaitLegacyListener<DownloadResult<T>> { listener ->
            testSubject.downloadRecords(recordIds, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val downloadResult = result.data
                assertEquals(2, downloadResult.successfulDownloads.size)
                assertTrue(downloadResult.failedDownloads.isEmpty())
                downloadResult.successfulDownloads.map {
                    assertRecordExpectations(it)
                    assertModelExpectations(it.fhirResource, Method.BATCH_DOWNLOAD)
                }
            }
            is Result.Failure -> {
                fail("Download records failed: ${result.exception.message}")
            }
        }
    }

    private suspend fun test_batch_07_deleteRecords_shouldDeleteRecords() {
        // Given

        // When
        val result = awaitLegacyListener<DeleteResult> { listener ->
            testSubject.deleteRecords(recordIds, listener)
        }

        // Then
        when (result) {
            is Result.Success -> {
                val deleteResult = result.data
                assertEquals(2, deleteResult.successfulDeletes.size)
                assertTrue(deleteResult.failedDeletes.isEmpty())
                for (i in 0..1) assertEquals(recordIds[i], deleteResult.successfulDeletes[i])
            }
            is Result.Failure -> {
                fail("Delete records failed: ${result.exception.message}")
            }
        }
    }

    // endregion

    abstract fun getTestClass(): Class<T>

    abstract fun getModel(method: Method, index: Int = -1): T

    abstract fun assertModelExpectations(
        model: T,
        method: Method,
        index: Int = -1
    )

    enum class Method {
        CREATE, BATCH_CREATE,
        FETCH, FETCH_BY_ID, FETCH_BY_TYPE,
        UPDATE, BATCH_UPDATE,
        DOWNLOAD, BATCH_DOWNLOAD,
        DELETE, BATCH_DELETE,
        COUNT
    }
}
