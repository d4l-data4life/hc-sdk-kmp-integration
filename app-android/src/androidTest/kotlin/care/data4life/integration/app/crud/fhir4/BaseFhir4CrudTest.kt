/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud.fhir4

import care.data4life.fhir.r4.model.DomainResource
import care.data4life.integration.app.crud.BaseCrudSdkTest
import care.data4life.integration.app.data.wrapper.Result
import care.data4life.integration.app.data.wrapper.Result.Failure
import care.data4life.integration.app.data.wrapper.Result.Success
import care.data4life.integration.app.data.wrapper.awaitCallback
import care.data4life.sdk.SdkContract.CreationDateRange
import care.data4life.sdk.SdkContract.UpdateDateTimeRange
import care.data4life.sdk.call.CallContract.Record
import care.data4life.sdk.call.Fhir4Record
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

abstract class BaseFhir4CrudTest<T : DomainResource> : BaseCrudSdkTest<T>() {

    override suspend fun callCreate(items: List<T>): List<Result<Fhir4Record<T>>> {
        val results: MutableList<Result<Fhir4Record<T>>> = mutableListOf()
        for (index in 0 until items.count()) {
            val result: Result<Fhir4Record<T>> = awaitCallback { callback ->
                testSubject.fhir4.create(items[index], getAnnotations(), callback)
            }

            results.add(mapToResult(result))
        }

        return results
    }

    override suspend fun callCount(): Result<Int> {
        return awaitCallback { callback ->
            testSubject.fhir4.count(getTestClass(), getAnnotations(), callback)
        }
    }

    override suspend fun callFetch(recordIds: List<String>): List<Result<Fhir4Record<T>>> {
        val results: MutableList<Result<Fhir4Record<T>>> = mutableListOf()
        for (index in 0 until recordIds.count()) {
            val result: Result<Fhir4Record<T>> = awaitCallback { callback ->
                testSubject.fhir4.fetch(recordIds[index], callback)
            }

            results.add(mapToResult(result))
        }

        return results
    }

    override suspend fun callFetchByType(): List<Result<Fhir4Record<T>>> {
        val result: Result<List<Fhir4Record<T>>> = awaitCallback { callback ->
            testSubject.fhir4.search(
                getTestClass(),
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

    override fun supportsDownload(): Boolean = true

    override suspend fun callUpdate(recordIds: List<String>, items: List<T>): List<Result<Record<T>>> {
        val results: MutableList<Result<Fhir4Record<T>>> = mutableListOf()
        for (index in 0 until items.count()) {
            val result: Result<Fhir4Record<T>> = awaitCallback { callback ->
                testSubject.fhir4.update(recordIds[index], items[index], getAnnotations(), callback)
            }

            results.add(mapToResult(result))
        }

        return results
    }

    override suspend fun callDownload(recordIds: List<String>): List<Result<Fhir4Record<T>>> {
        val results: MutableList<Result<Fhir4Record<T>>> = mutableListOf()
        for (index in 0 until recordIds.count()) {
            val result: Result<Fhir4Record<T>> = awaitCallback { callback ->
                testSubject.fhir4.download(recordIds[index], callback)
            }

            results.add(mapToResult(result))
        }

        return results
    }

    override suspend fun callDelete(recordIds: List<String>): List<Result<String>> {
        val results: MutableList<Result<String>> = mutableListOf()
        for (index in 0 until recordIds.count()) {
            val result: Result<Boolean> = awaitCallback { callback ->
                testSubject.fhir4.delete(recordIds[index], callback)
            }

            when (result) {
                is Success -> results.add(Success(recordIds[index]))
                is Failure -> Failure(result.exception)
            }
        }

        return results
    }

    private fun mapToResult(result: Result<Fhir4Record<T>>): Result<Fhir4Record<T>> {
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

    private fun mapToResults(result: Result<*>, records: () -> List<Fhir4Record<T>>): List<Result<Fhir4Record<T>>> {
        val results = mutableListOf<Result<Fhir4Record<T>>>()

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

    private fun mapToRecord(result: Fhir4Record<T>): Fhir4Record<T> {
        return Fhir4Record(
            identifier = result.identifier,
            resource = result.resource,
            meta = result.meta,
            annotations = result.annotations
        )
    }
}
