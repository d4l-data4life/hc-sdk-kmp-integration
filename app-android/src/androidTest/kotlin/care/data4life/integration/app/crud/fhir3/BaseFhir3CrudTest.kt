/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud.fhir3

import care.data4life.fhir.stu3.model.DomainResource
import care.data4life.integration.app.crud.BaseCrudSdkTest
import care.data4life.integration.app.data.wrapper.Result
import care.data4life.integration.app.data.wrapper.Result.Failure
import care.data4life.integration.app.data.wrapper.Result.Success
import care.data4life.integration.app.data.wrapper.awaitLegacyCallback
import care.data4life.integration.app.data.wrapper.awaitLegacyListener
import care.data4life.sdk.SdkContract.CreationDateRange
import care.data4life.sdk.call.CallContract.Record
import care.data4life.sdk.call.Fhir3Record
import care.data4life.sdk.model.CreateResult
import care.data4life.sdk.model.DeleteResult
import care.data4life.sdk.model.DownloadResult
import care.data4life.sdk.model.FetchResult
import care.data4life.sdk.model.UpdateResult
import org.threeten.bp.LocalDate
import care.data4life.sdk.model.Record as LegacyRecord

abstract class BaseFhir3CrudTest<T : DomainResource> : BaseCrudSdkTest<T>() {

    override suspend fun callCreate(items: List<T>): List<Result<Record<T>>> {
        val results: MutableList<Result<Record<T>>> = mutableListOf()
        if (items.count() == 1) {
            val legacyResult: Result<LegacyRecord<T>> = awaitLegacyListener { listener ->
                testSubject.createRecord(items[0], listener)
            }

            results.add(mapToResult(legacyResult))
        } else {
            val legacyResult: Result<CreateResult<T>> = awaitLegacyListener { listener ->
                testSubject.createRecords(items, listener)
            }

            results.addAll(
                mapToResults(legacyResult) {
                    if (legacyResult is Success) legacyResult.data.successfulOperations
                    else emptyList()
                }
            )
        }

        return results
    }

    override suspend fun callCount(): Result<Int> {
        return awaitLegacyListener { listener ->
            testSubject.countRecords(getTestClass(), listener)
        }
    }

    override suspend fun callFetch(recordIds: List<String>): List<Result<Record<T>>> {
        val results: MutableList<Result<Record<T>>> = mutableListOf()
        if (recordIds.count() == 1) {
            val legacyResult: Result<LegacyRecord<T>> = awaitLegacyListener { listener ->
                testSubject.fetchRecord(recordIds[0], listener)
            }

            results.add(mapToResult(legacyResult))
        } else {
            val legacyResult: Result<FetchResult<T>> = awaitLegacyListener { listener ->
                testSubject.fetchRecords(recordIds, listener)
            }

            results.addAll(
                mapToResults(legacyResult) {
                    if (legacyResult is Success) legacyResult.data.successfulFetches
                    else emptyList()
                }
            )
        }

        return results
    }

    override suspend fun callFetchByType(): List<Result<Record<T>>> {
        val legacyResult: Result<List<LegacyRecord<T>>> = awaitLegacyListener { listener ->
            testSubject.fetchRecords(
                getTestClass(),
                CreationDateRange(
                    LocalDate.now().minusYears(1),
                    LocalDate.now()
                ),
                null,
                false,
                1000,
                0,
                listener
            )
        }

        return mapToResults(legacyResult) {
            if (legacyResult is Success) legacyResult.data
            else emptyList()
        }
    }

    override fun supportsDownload(): Boolean = true

    override suspend fun callUpdate(recordIds: List<String>, items: List<T>): List<Result<Record<T>>> {
        val results: MutableList<Result<Record<T>>> = mutableListOf()
        if (items.count() == 1) {
            val legacyResult: Result<LegacyRecord<T>> = awaitLegacyListener { listener ->
                testSubject.updateRecord(items[0], listener)
            }

            results.add(mapToResult(legacyResult))
        } else {
            val legacyResult: Result<UpdateResult<T>> = awaitLegacyListener { listener ->
                testSubject.updateRecords(items, listener)
            }

            results.addAll(
                mapToResults(legacyResult) {
                    if (legacyResult is Success) legacyResult.data.successfulUpdates
                    else emptyList()
                }
            )
        }

        return results
    }

    override suspend fun callDownload(recordIds: List<String>): List<Result<Record<T>>> {
        val results: MutableList<Result<Record<T>>> = mutableListOf()
        if (recordIds.count() == 1) {
            val legacyResult: Result<LegacyRecord<T>> = awaitLegacyListener { listener ->
                testSubject.downloadRecord(recordIds[0], listener)
            }

            results.add(mapToResult(legacyResult))
        } else {
            val legacyResult: Result<DownloadResult<T>> = awaitLegacyListener { listener ->
                testSubject.downloadRecords(recordIds, listener)
            }

            results.addAll(
                mapToResults(legacyResult) {
                    if (legacyResult is Success) legacyResult.data.successfulDownloads
                    else emptyList()
                }
            )
        }

        return results
    }

    override suspend fun callDelete(recordIds: List<String>): List<Result<String>> {
        val results: MutableList<Result<String>> = mutableListOf()

        if (recordIds.count() == 1) {
            val result: Result<Boolean> = awaitLegacyCallback { callback ->
                testSubject.deleteRecord(recordIds[0], callback)
            }

            when (result) {
                is Success -> results.add(Success(recordIds[0]))
                is Failure -> Failure(result.exception)
            }
        } else {
            val legacyResult: Result<DeleteResult> = awaitLegacyListener { listener ->
                testSubject.deleteRecords(recordIds, listener)
            }

            when (legacyResult) {
                is Success -> {
                    val data = legacyResult.data.successfulDeletes.map { Success(it) }
                    results.addAll(data)
                }
                is Failure -> results.add(Failure(legacyResult.exception))
            }
        }

        return results
    }

    private fun mapToResult(legacyResult: Result<LegacyRecord<T>>): Result<Record<T>> {
        return when (legacyResult) {
            is Success -> {
                val data = mapToRecord(legacyResult.data)
                Success(data)
            }
            is Failure -> {
                Failure(legacyResult.exception)
            }
        }
    }

    private fun mapToResults(result: Result<*>, records: () -> List<LegacyRecord<T>>): List<Result<Record<T>>> {
        val results = mutableListOf<Result<Record<T>>>()

        when (result) {
            is Success -> {
                val data = records().map { mapToRecord(it) }.map { Success(it) }
                results.addAll(data)
            }
            is Failure -> {
                results.add(Failure(result.exception))
            }
        }

        return results
    }

    private fun mapToRecord(legacyRecord: LegacyRecord<T>): Record<T> {
        return Fhir3Record(
            identifier = legacyRecord.identifier,
            resource = legacyRecord.fhirResource,
            meta = legacyRecord.meta!!,
            annotations = legacyRecord.annotations!!
        )
    }
}
