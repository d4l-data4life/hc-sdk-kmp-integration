/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import care.data4life.fhir.stu3.model.DomainResource
import care.data4life.sdk.model.Meta
import care.data4life.sdk.model.Record
import org.junit.jupiter.api.Assertions

object RecordAssertions {

    fun <T : DomainResource> assertRecordExpectations(record: Record<T>) {
        Assertions.assertNotNull(record.fhirResource)
        Assertions.assertNotNull(record.fhirResource.id)
        assertMetaExpectations(record.meta as Meta?)
    }

    private fun assertMetaExpectations(meta: Meta?) {
        Assertions.assertNotNull(meta)
        Assertions.assertNotNull(meta?.createdDate)
        Assertions.assertNotNull(meta?.updatedDate)
    }
}
