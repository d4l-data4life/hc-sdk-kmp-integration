/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.testUtils

import care.data4life.fhir.stu3.model.DomainResource
import care.data4life.sdk.Data4LifeClient
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.ResultListener
import care.data4life.sdk.model.DeleteResult
import care.data4life.sdk.model.Record
import kotlinx.coroutines.runBlocking
import org.threeten.bp.LocalDate
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


fun <T : DomainResource> Data4LifeClient.deleteAllRecords(clazz: Class<T>) {
    runBlocking {
        val records = fetchRecords(clazz)
        deleteRecords(records.map { it.fhirResource.id!! })
    }
}

private suspend fun <T : DomainResource> fetchRecords(clazz: Class<T>): List<Record<T>> = suspendCoroutine { cont ->
    Data4LifeClient.getInstance().fetchRecords(
            clazz,
            LocalDate.now().minusYears(10),
            LocalDate.now(),
            1000,
            0,
            object : ResultListener<List<Record<T>>> {

                override fun onSuccess(records: List<Record<T>>) {
                    cont.resume(records)
                }

                override fun onError(exception: D4LException) {
                    exception.printStackTrace()
                    cont.resumeWithException(exception)
                }
            })
}

private suspend fun deleteRecords(recordIds: List<String>): DeleteResult = suspendCoroutine { cont ->
    Data4LifeClient.getInstance().deleteRecords(recordIds, object : ResultListener<DeleteResult> {
        override fun onSuccess(result: DeleteResult) {
            cont.resume(result)
        }

        override fun onError(exception: D4LException) {
            exception.printStackTrace()
            cont.resumeWithException(exception)
        }
    })
}
