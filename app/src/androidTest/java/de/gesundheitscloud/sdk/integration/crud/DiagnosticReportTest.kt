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

import de.gesundheitscloud.fhir.stu3.model.*
import de.gesundheitscloud.fhir.stu3.util.FhirDateTimeParser
import de.gesundheitscloud.sdk.helpers.DiagnosticReportBuilder
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DiagnosticReportTest : BaseTest<DiagnosticReport>() {
    //region report properties
    val reportTypeCode = "GHP"
    val reportTypeDisplay = "General Health Profile"
    val reportTypeSystem = "http://acme.com/labs/reports"
    val reportStatus = CodeSystems.DiagnosticReportStatus.FINAL
    val laboratoryName = "Acme Laboratory, Inc"
    val observationId1 = "id1"
    val observationId2 = "id2"

    val issuedDate = FhirDateTimeParser.parseInstant("2013-04-03T15:30:10+01:00")
    val startingCharacter = "#"
    //endregion

    //region obvservation properties
    val observationTypeCode = "9279-1"
    val observationTypeDisplay = "Respiratory rate"
    val observationTypeSystem = "http://loinc.org"
    val observationTypeText = "Respiratory rate"
    val observationStatus = CodeSystems.ObservationStatus.FINAL
    //endregion

    override fun getTestClass(): Class<DiagnosticReport> {
        return DiagnosticReport::class.java
    }

    override fun getModel(method: Method, index: Int): DiagnosticReport {
        val reportCoding = Coding().apply {
            code = reportTypeCode
            display = reportTypeDisplay
            system = reportTypeSystem
        }
        val reportCode = CodeableConcept()
        reportCode.coding = mutableListOf(reportCoding)

        val report = DiagnosticReportBuilder.buildWith(
                reportCode,
                reportStatus,
                laboratoryName,
                issuedDate,
                listOf(getTestObservation(observationId1), getTestObservation(observationId2)))
        mutateModel(report, method, index)

        return report
    }

    private fun getTestObservation(customId: String): Observation {
        val observationCoding = Coding().apply {
            code = observationTypeCode
            display = observationTypeDisplay
            system = observationTypeSystem
        }
        val observationCode = CodeableConcept().apply {
            text = observationTypeText
            coding = listOf(observationCoding)
        }
        return Observation(observationCode, observationStatus).apply {
            id = customId
        }
    }

    private fun mutateModel(model: DiagnosticReport, method: Method, index: Int) {
        when (method) {
            Method.UPDATE -> {
                model.performer?.first()?.actor?.display = "new lab name"
                model.id = recordId
            }
            Method.BATCH_UPDATE -> {
                model.performer?.first()?.actor?.display = (if (index == 0) "lab1" else "lab2")
                model.id = recordId
            }
            else -> {
                //ignore
            }
        }
    }

    override fun assertModelExpectations(model: DiagnosticReport, method: Method, index: Int) {
        var assertRecordId = true
        var labName = laboratoryName

        when (method) {
            Method.BATCH_CREATE -> {
                assertNotNull(model.id)
                assertRecordId = false
            }
            Method.FETCH_BY_TYPE -> {
                assertNotNull(model.id)
                assertRecordId = false
            }
            Method.UPDATE -> {
                labName = "new lab name"
            }
            Method.BATCH_UPDATE -> {
                labName = if (index == 0) "lab1" else "lab2"
            }
            Method.DOWNLOAD -> {
                labName = "lab2"
            }
            Method.BATCH_DOWNLOAD -> {
                labName = "lab2"
            }
            else -> {
                //ignore
            }
        }
        assertReportExpectations(model, assertRecordId, labName)
    }

    private fun assertReportExpectations(report: DiagnosticReport, assertRecordId: Boolean, labName: String) {
        assertEquals(1, report.code?.coding?.size)
        assertEquals(reportTypeCode, report.code?.coding?.first()?.code)
        assertEquals(reportTypeDisplay, report.code?.coding?.first()?.display)
        assertEquals(reportTypeSystem, report.code?.coding?.first()?.system)
        assertEquals(1, report.performer?.size)
        assertEquals(labName, report.performer?.first()?.actor?.display)
        assertEquals(issuedDate, report.issued)
        assertEquals(2, report.result?.size)
        assertEquals("$startingCharacter$observationId1", report.result?.get(0)?.reference)
        assertEquals("$startingCharacter$observationId2", report.result?.get(1)?.reference)
        assertEquals(2, report.contained?.size)

        assertTrue(report.contained?.get(0) is Observation)
        assertObservationExpectations(report.contained?.get(0) as Observation, observationId1)
        assertTrue(report.contained?.get(1) is Observation)
        assertObservationExpectations(report.contained?.get(1) as Observation, observationId2)
    }

    private fun assertObservationExpectations(observation: Observation, id: String) {
        assertEquals(id, observation.id)
        assertEquals(1, observation.code.coding?.size)
        assertEquals(observationTypeText, observation.code.text)
        val observationCoding = observation.code.coding?.first()
        assertEquals(observationTypeCode, observationCoding?.code)
        assertEquals(observationTypeDisplay, observationCoding?.display)
        assertEquals(observationTypeSystem, observationCoding?.system)
    }
}
