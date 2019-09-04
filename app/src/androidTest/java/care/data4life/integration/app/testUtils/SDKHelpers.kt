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

package care.data4life.integration.app.testUtils

import care.data4life.fhir.stu3.model.DomainResource
import de.gesundheitscloud.sdk.HealthCloudAndroid
import de.gesundheitscloud.sdk.lang.D4LException
import de.gesundheitscloud.sdk.listener.ResultListener
import de.gesundheitscloud.sdk.model.DeleteResult
import de.gesundheitscloud.sdk.model.Record
import kotlinx.coroutines.runBlocking
import org.threeten.bp.LocalDate
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


fun <T : DomainResource> HealthCloudAndroid.deleteAllRecords(clazz: Class<T>) {
    runBlocking {
        val records = fetchRecords(clazz)
        deleteRecords(records.map { it.fhirResource.id!! })
    }
}

private suspend fun <T : DomainResource> fetchRecords(clazz: Class<T>): List<Record<T>> = suspendCoroutine { cont ->
    HealthCloudAndroid.getInstance().fetchRecords(
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
    HealthCloudAndroid.getInstance().deleteRecords(recordIds, object : ResultListener<DeleteResult> {
        override fun onSuccess(result: DeleteResult) {
            cont.resume(result)
        }

        override fun onError(exception: D4LException) {
            exception.printStackTrace()
            cont.resumeWithException(exception)
        }
    })
}
