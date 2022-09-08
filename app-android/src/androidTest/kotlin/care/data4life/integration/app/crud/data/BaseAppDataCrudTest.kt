/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud.data

import care.data4life.integration.app.crud.BaseCrudSdkTest
import care.data4life.integration.app.data.wrapper.Result
import care.data4life.integration.app.data.wrapper.Result.Failure
import care.data4life.integration.app.data.wrapper.Result.Success
import care.data4life.integration.app.data.wrapper.awaitCallback
import care.data4life.sdk.SdkContract.CreationDateRange
import care.data4life.sdk.SdkContract.UpdateDateTimeRange
import care.data4life.sdk.call.CallContract.Record
import care.data4life.sdk.call.DataRecord
import care.data4life.sdk.data.DataResource
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

abstract class BaseAppDataCrudTest : BaseCrudSdkTest<DataResource>() {

    override suspend fun callCreate(items: List<DataResource>): List<Result<DataRecord<DataResource>>> {
        val results: MutableList<Result<DataRecord<DataResource>>> = mutableListOf()
        for (index in 0 until items.count()) {
            val result: Result<DataRecord<DataResource>> = awaitCallback { callback ->
                testSubject.data.create(items[index], getAnnotations(), callback)
            }

            results.add(mapToResult(result))
        }

        return results
    }

    override suspend fun callCount(): Result<Int> {
        return awaitCallback { callback ->
            testSubject.data.count(getAnnotations(), callback)
        }
    }

    override suspend fun callFetch(recordIds: List<String>): List<Result<DataRecord<DataResource>>> {
        val results: MutableList<Result<DataRecord<DataResource>>> = mutableListOf()
        for (index in 0 until recordIds.count()) {
            val result: Result<DataRecord<DataResource>> = awaitCallback { callback ->
                testSubject.data.fetch(recordIds[index], callback)
            }

            results.add(mapToResult(result))
        }

        return results
    }

    override suspend fun callFetchByType(): List<Result<DataRecord<DataResource>>> {
        val result: Result<List<DataRecord<DataResource>>> = awaitCallback { callback ->
            testSubject.data.search(
                getAnnotations(),
                CreationDateRange(
                    LocalDate.now().minusYears(1),
                    LocalDate.now()
                ),
                UpdateDateTimeRange(
                    LocalDateTime.now().minusDays(2),
                    LocalDateTime.now()
                ),
                false,
                1000,
                0,
                callback
            )
        }

        return mapToResults(result) {
            if (result is Success) result.data
            else emptyList()
        }
    }

    override fun supportsDownload(): Boolean = false

    override suspend fun callDownload(recordIds: List<String>): List<Result<Record<DataResource>>> {
        throw UnsupportedOperationException("callDownload is not supported to AppData")
    }

    override suspend fun callUpdate(
        recordIds: List<String>,
        items: List<DataResource>
    ): List<Result<DataRecord<DataResource>>> {
        val results: MutableList<Result<DataRecord<DataResource>>> = mutableListOf()
        for (index in 0 until items.count()) {
            val result: Result<DataRecord<DataResource>> = awaitCallback { callback ->
                testSubject.data.update(recordIds[index], items[index], getAnnotations(), callback)
            }

            results.add(mapToResult(result))
        }

        return results
    }

    override suspend fun callDelete(recordIds: List<String>): List<Result<String>> {
        val results: MutableList<Result<String>> = mutableListOf()
        for (index in 0 until recordIds.count()) {
            val result: Result<Boolean> = awaitCallback { callback ->
                testSubject.data.delete(recordIds[index], callback)
            }

            when (result) {
                is Success -> results.add(Success(recordIds[index]))
                is Failure -> Failure(result.exception)
            }
        }

        return results
    }

    private fun mapToResult(result: Result<DataRecord<DataResource>>): Result<DataRecord<DataResource>> {
        return when (result) {
            is Success -> {
                val data = mapToRecord(result.data)
                Success(data)
            }
            is Failure -> {
                Failure(result.exception)
            }
        }
    }

    private fun mapToResults(
        result: Result<*>,
        records: () -> List<DataRecord<DataResource>>
    ): List<Result<DataRecord<DataResource>>> {
        val results = mutableListOf<Result<DataRecord<DataResource>>>()

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

    private fun mapToRecord(result: DataRecord<DataResource>): DataRecord<DataResource> {
        return DataRecord(
            identifier = result.identifier,
            resource = result.resource,
            meta = result.meta,
            annotations = result.annotations
        )
    }
}
