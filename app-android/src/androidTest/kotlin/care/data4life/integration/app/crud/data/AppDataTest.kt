/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud.data

import care.data4life.sdk.call.CallContract.Record
import care.data4life.sdk.data.DataResource
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class AppDataTest : BaseAppDataCrudTest() {

    @Test
    fun testAll() = extension.runComposeTest {
        login()

        assertLoggedIn(true)

        runBlocking {
            runTestSuite(1)

            // runTestSuite(10)
        }

        logout()
    }

    override fun assertCreateRecord(expected: DataResource, actual: Record<DataResource>) {
        assertDataResource(expected, actual.resource)
    }

    override fun assertFetchRecord(expected: DataResource, actual: Record<DataResource>) {
        assertDataResource(expected, actual.resource)
    }

    override fun assertFetchByType(expected: DataResource, actual: Record<DataResource>) {
        assertDataResource(expected, actual.resource)
    }

    override fun assertUpdate(expected: DataResource, actual: Record<DataResource>) {
        assertDataResource(expected, actual.resource)
    }

    override fun assertDownload(expected: DataResource, actual: Record<DataResource>) {
        assertDataResource(expected, actual.resource)
    }

    override fun getTestClass(): Class<DataResource> {
        return DataResource::class.java
    }

    override fun getId(record: Record<DataResource>): String {
        return record.identifier
    }

    override fun generateItem(): DataResource {
        return DataResource(data)
    }

    override fun mutateItem(item: DataResource): DataResource {
        return item.copy(dataMutated)
    }

    private fun assertDataResource(expected: DataResource, actual: DataResource) {
        assertTrue { expected.value.contentEquals(actual.value) }
    }

    companion object {
        val data = byteArrayOf(0x25, 0x50, 0x44, 0x46, 0x2d)
        val dataMutated = data.reversedArray()
    }
}
