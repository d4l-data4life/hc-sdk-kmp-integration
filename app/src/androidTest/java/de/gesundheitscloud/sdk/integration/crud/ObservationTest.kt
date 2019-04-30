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

import androidx.test.runner.AndroidJUnit4
import de.gesundheitscloud.fhir.stu3.model.CodeSystems
import de.gesundheitscloud.fhir.stu3.model.CodeableConcept
import de.gesundheitscloud.fhir.stu3.model.Coding
import de.gesundheitscloud.fhir.stu3.model.Observation
import de.gesundheitscloud.fhir.stu3.util.FhirDateTimeParser
import de.gesundheitscloud.sdk.helpers.ObservationBuilder
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class ObservationTest : BaseTest<Observation>() {
    //region observation properties
    val categoryCode = "vital-signs"
    val categoryDisplay = "Vital Signs"
    val categorySystem = "http://hl7.org/fhir/observation-category"
    val categoryText = "Vital Signs"
    val observationTypeCode = "9279-1"
    val observationTypeDisplay = "Respiratory rate"
    val observationTypeSystem = "http://loinc.org"
    val observationTypeText = "Respiratory rate"
    val observationUnit = "breaths/minute"
    val observationValue = 26f
    val observationStatus = CodeSystems.ObservationStatus.FINAL
    val issuedDate = FhirDateTimeParser.parseInstant("2013-04-03T15:30:10+01:00")
    val effectiveDate = FhirDateTimeParser.parseDateTime("2013-04-03")
    //endregion

    override fun getTestClass(): Class<Observation> {
        return Observation::class.java
    }

    override fun getModel(method: Method, index: Int): Observation {
        val observationCoding = Coding().apply {
            code = observationTypeCode
            display = observationTypeDisplay
            system = observationTypeSystem
        }
        val observationCode = CodeableConcept().apply {
            text = observationTypeText
            coding = mutableListOf(observationCoding)
        }

        val categoryCoding = Coding().apply {
            code = categoryCode
            display = categoryDisplay
            system = categorySystem
        }
        val categoryCodeable = CodeableConcept().apply {
            text = categoryText
            coding = mutableListOf(categoryCoding)
        }

        val issuedDate = FhirDateTimeParser.parseInstant("2013-04-03T15:30:10+01:00")
        val effectiveDate = FhirDateTimeParser.parseDateTime("2013-04-03")

        val observation = ObservationBuilder.buildWith(
                observationCode,
                observationValue,
                observationUnit,
                observationStatus,
                issuedDate,
                effectiveDate,
                categoryCodeable)
        mutateModel(observation, method, index)

        return observation
    }

    private fun mutateModel(model: Observation, method: Method, index: Int) {
        when (method) {
            Method.UPDATE -> {
                model.valueQuantity?.unit = "new unit"
                model.id = recordId
            }
            Method.BATCH_UPDATE -> {
                model.valueQuantity?.unit = (if (index == 0) "unit1" else "unit2")
                model.id = recordId
            }
            else -> {
                //ignore
            }
        }
    }

    override fun assertModelExpectations(model: Observation, method: Method, index: Int) {
        var assertRecordId = true
        var unit = observationUnit

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
                unit = "new unit"
            }
            Method.BATCH_UPDATE -> {
                unit = if (index == 0) "unit1" else "unit2"
            }
            Method.DOWNLOAD -> {
                unit = "unit2"
            }
            Method.BATCH_DOWNLOAD -> {
                unit = "unit2"
            }
            else -> {
                //ignore
            }
        }
        assertObservationExpectations(model, assertRecordId, unit)
    }

    private fun assertObservationExpectations(observation: Observation, assertRecordId: Boolean, unit: String) {
        if (assertRecordId) assertEquals(recordId, observation.id)
        assertEquals(observationTypeText, observation.code?.text)
        assertEquals(1, observation.code?.coding?.size)
        assertEquals(observationTypeCode, observation.code?.coding?.first()?.code)
        assertEquals(observationTypeDisplay, observation.code?.coding?.first()?.display)
        assertEquals(observationTypeSystem, observation.code?.coding?.first()?.system)

        assertEquals(observationValue, observation.valueQuantity?.value?.decimal?.toFloat())
        assertEquals(unit, observation.valueQuantity?.unit)
        assertEquals(observationStatus, observation.status)
        assertEquals(issuedDate, observation.issued)
        assertEquals(effectiveDate, observation.effectiveDateTime)

        assertEquals(1, observation.category?.size)
        assertEquals(categoryText, observation.category?.first()?.text)
        assertEquals(1, observation.category?.first()?.coding?.size)
        assertEquals(categoryCode, observation.category?.first()?.coding?.first()?.code)
        assertEquals(categoryDisplay, observation.category?.first()?.coding?.first()?.display)
        assertEquals(categorySystem, observation.category?.first()?.coding?.first()?.system)
    }
}
