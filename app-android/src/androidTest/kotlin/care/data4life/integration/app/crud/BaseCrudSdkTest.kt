/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import care.data4life.integration.app.data.wrapper.Result
import care.data4life.integration.app.data.wrapper.Result.Failure
import care.data4life.integration.app.data.wrapper.Result.Success
import care.data4life.integration.app.data.wrapper.awaitLegacyListener
import care.data4life.integration.app.test.BaseSdkTest
import care.data4life.sdk.call.CallContract.Record
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.fail
import kotlin.test.assertEquals
import care.data4life.sdk.model.DeleteResult as LegacyDeleteResult

abstract class BaseCrudSdkTest<T> : BaseSdkTest() {

    protected suspend fun runTestSuite(itemCount: Int) {
        cleanAccount()

        val initialItems = generateItems(itemCount)

        val createdRecords = create(initialItems)

        val createdItems = createdRecords.map { it.resource }
        val recordIds = createdRecords.map { getId(it)!! }

        count(itemCount)
        fetch(recordIds, createdItems)
        val loadedItems = fetchByType(itemCount, createdItems)

        val mutatedItems = mutateItems(loadedItems)

        update(recordIds, mutatedItems)
        if (supportsDownload()) {
            download(recordIds, mutatedItems)
        }

        delete(recordIds)
        count(0)
    }

    abstract fun getAnnotations(): List<String>

    protected suspend fun create(items: List<T>): List<Record<T>> {
        // Given
        val createdRecords = mutableListOf<Record<T>>()

        // When
        val results: List<Result<Record<T>>> = callCreate(items)

        // Then
        assertResults(results, "Create") { index, record ->
            assertCreateRecord(items[index], record)
            createdRecords.add(record)
        }

        return createdRecords
    }

    abstract suspend fun callCreate(items: List<T>): List<Result<Record<T>>>
    abstract fun assertCreateRecord(expected: T, actual: Record<T>)

    protected suspend fun count(count: Int) {
        // Given

        // When
        val result: Result<Int> = callCount()

        // Then
        when (result) {
            is Success -> {
                Assertions.assertEquals(count, result.data)
            }
            is Failure -> {
                fail("Count records failed: ${result.exception.message}")
            }
        }
    }

    abstract suspend fun callCount(): Result<Int>

    protected suspend fun fetch(recordIds: List<String>, items: List<T>): List<Result<Record<T>>> {
        // Given

        // When
        val results: List<Result<Record<T>>> = callFetch(recordIds)

        // Then
        assertEquals(recordIds.size, results.size)
        assertResults(results, "Fetch") { index, record ->
            assertFetchRecord(items[index], record)
        }

        return results
    }

    abstract suspend fun callFetch(recordIds: List<String>): List<Result<Record<T>>>
    abstract fun assertFetchRecord(expected: T, actual: Record<T>)

    protected suspend fun fetchByType(count: Int, items: List<T>): List<T> {
        // Given
        val loadedItems = mutableListOf<T>()

        // When
        val results: List<Result<Record<T>>> = callFetchByType()

        // Then
        assertEquals(count, results.size)
        assertResults(results, "FetchByType") { index, record ->
            assertFetchByType(items[index], record)
            loadedItems.add(record.resource)
        }

        return loadedItems
    }

    abstract suspend fun callFetchByType(): List<Result<Record<T>>>
    abstract fun assertFetchByType(expected: T, actual: Record<T>)

    private suspend fun update(recordIds: List<String>, items: List<T>) {
        // Given

        // When
        val results: List<Result<Record<T>>> = callUpdate(recordIds, items)

        // Then
        assertEquals(items.size, results.size)
        assertResults(results, "Update") { index, record ->
            assertUpdate(items[index], record)
        }
    }

    abstract suspend fun callUpdate(recordIds: List<String>, items: List<T>): List<Result<Record<T>>>
    abstract fun assertUpdate(expected: T, actual: Record<T>)

    abstract fun supportsDownload(): Boolean
    private suspend fun download(recordIds: List<String>, items: List<T>) {
        // Given

        // When
        val results: List<Result<Record<T>>> = callDownload(recordIds)

        // Then
        assertEquals(recordIds.size, results.size)
        assertResults(results, "Download") { index, record ->
            assertDownload(items[index], record)
        }
    }

    abstract suspend fun callDownload(recordIds: List<String>): List<Result<Record<T>>>
    abstract fun assertDownload(expected: T, actual: Record<T>)

    private suspend fun delete(recordIds: List<String>) {
        // Given

        // When
        val results: List<Result<String>> = callDelete(recordIds)

        // Then
        assertEquals(recordIds.size, results.size)
        results.forEachIndexed { index, result ->
            when (result) {
                is Success -> assertEquals(recordIds[index], result.data)
                is Failure -> fail("Delete records (${recordIds[index]}) failed: ${result.exception.message}")
            }
        }
    }

    abstract suspend fun callDelete(recordIds: List<String>): List<Result<String>>

    protected suspend fun cleanAccount() {
        val results: List<Result<Record<T>>> = callFetchByType()

        val ids = results.filter { it is Success }
            .map {
                getId((it as Success).data)!!
            }

        if (ids.isNotEmpty()) {
            deleteRecords(ids)
        }
    }

    private suspend fun deleteRecords(recordIds: List<String>) {
        awaitLegacyListener<LegacyDeleteResult> { listener ->
            testSubject.deleteRecords(recordIds, listener)
        }
    }

    private fun assertResults(
        results: List<Result<Record<T>>>,
        operationName: String,
        check: (Int, Record<T>) -> Unit
    ) {
        for ((index, result) in results.withIndex()) {
            when (result) {
                is Success -> {
                    val record = result.data
                    check(index, record)
                }
                is Failure -> fail("$operationName record failed: ${result.exception.message}")
            }
        }
    }

    abstract fun getTestClass(): Class<T>

    abstract fun getId(record: Record<T>): String?

    abstract fun generateItem(): T

    protected fun generateItems(count: Int): List<T> {
        val items = mutableListOf<T>()
        for (i in 1..count) {
            items.add(generateItem())
        }
        return items
    }

    abstract fun mutateItem(item: T): T

    private fun mutateItems(items: List<T>): List<T> {
        return items.map { mutateItem(it) }
    }
}
