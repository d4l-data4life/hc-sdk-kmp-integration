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

import androidx.test.rule.ActivityTestRule
import com.jakewharton.threetenabp.AndroidThreeTen
import de.gesundheitscloud.fhir.stu3.model.DomainResource
import de.gesundheitscloud.sdk.HCException
import de.gesundheitscloud.sdk.HealthCloudAndroid
import de.gesundheitscloud.sdk.integration.MainActivity
import de.gesundheitscloud.sdk.integration.page.BasePage.Companion.TIMEOUT
import de.gesundheitscloud.sdk.integration.page.HomePage
import de.gesundheitscloud.sdk.integration.page.WelcomePage
import de.gesundheitscloud.sdk.integration.testUtils.NetworkUtil
import de.gesundheitscloud.sdk.integration.testUtils.deleteAllRecords
import de.gesundheitscloud.sdk.listener.Callback
import de.gesundheitscloud.sdk.listener.ResultListener
import de.gesundheitscloud.sdk.model.*
import junit.framework.Assert.*
import org.junit.*
import org.junit.Assume.assumeTrue
import org.junit.runners.MethodSorters
import org.threeten.bp.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertNotEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //test order is important for successful completion!
abstract class BaseTest<T : DomainResource> {
    private val TIMEOUT = 10L
    private lateinit var model: T
    private var requestSuccessful = true

    abstract fun getModelClass(): Class<T>

    abstract fun getTestModel(): T

    abstract fun prepareModel(model: T, method: Method, index: Int = -1)

    abstract fun assertModelExpectations(model: T, method: Method, index: Int = -1)

    enum class Method {
        CREATE, BATCH_CREATE,
        FETCH, FETCH_BY_ID, FETCH_BY_TYPE,
        UPDATE, BATCH_UPDATE,
        DOWNLOAD, BATCH_DOWNLOAD,
        DELETE, BATCH_DELETE,
        COUNT
    }

    companion object {
        private var setupDone = false
        private var isNetConnected: Boolean = false
        private lateinit var latch: CountDownLatch
        private val rule = ActivityTestRule(MainActivity::class.java, false, false)
        private lateinit var activity: MainActivity
        private lateinit var homePage: HomePage

        @JvmStatic
        protected lateinit var recordId: String
        @JvmStatic
        protected var recordIds = mutableListOf<String>()

        @JvmStatic
        protected lateinit var client: HealthCloudAndroid  //SUT

        @BeforeClass
        @JvmStatic
        fun suiteSetup() {
            isNetConnected = NetworkUtil.isOnline()
            assumeTrue("Internet connection required", isNetConnected)

            activity = rule.launchActivity(null)
            AndroidThreeTen.init(activity.application)
            client = HealthCloudAndroid.getInstance()

            homePage = WelcomePage()
                    .isVisible()
                    .openLoginPage()
                    .doLogin("igy.testing@gmail.com", "1234567+")
                    .isVisible()

            assertLogin(true)
        }

        @AfterClass
        @JvmStatic
        fun suiteCleanUp() {
            if (!isNetConnected) return

            homePage
                    .doLogout()
                    .isVisible()

            assertLogin(false)
            activity.explicitFinish()
        }

        private fun assertLogin(expectedLoggedInState: Boolean) {
            var isLoggedIn: Boolean = false
            latch = CountDownLatch(1)
            client.isUserLoggedIn(object : ResultListener<Boolean> {
                override fun onSuccess(loggedIn: Boolean) {
                    isLoggedIn = loggedIn
                    latch.countDown()
                }

                override fun onError(exception: HCException) {
                    exception.printStackTrace()
                    latch.countDown()
                }
            })
            latch.await(TIMEOUT, TimeUnit.SECONDS)

            if (expectedLoggedInState) assertTrue(isLoggedIn)
            else assertFalse(isLoggedIn)
        }
    }

    @Before
    fun beforeTest() {
        latch = CountDownLatch(1)
        model = getTestModel()
        requestSuccessful = true

        if (setupDone) return
        else {
            setupDone = true
            client.deleteAllRecords(getModelClass()) //run only once before all the tests
        }
    }

    abstract inner class TestResultListener<V> : ResultListener<V> {
        override fun onError(exception: HCException) {
            exception.printStackTrace()
            requestSuccessful = false
            latch.countDown()
        }
    }

    private fun assertRecordExpectations(record: Record<T>) {
        assertNotNull(record.fhirResource)
        assertMetaExpectations(record.meta)
    }

    private fun assertMetaExpectations(meta: Meta?) {
        assertNotNull(meta)
        assertNotNull(meta?.createdDate)
        assertNotNull(meta?.updatedDate)
    }

    @Test
    fun t01_createRecord_shouldReturn_createdRecord() {
        lateinit var record: Record<T>

        //when
        client.createRecord(model, object : TestResultListener<Record<T>>() {
            override fun onSuccess(r: Record<T>) {
                record = r
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Create record failed", requestSuccessful)
        assertRecordExpectations(record)
        assertNotNull(record.fhirResource.id)
        recordId = record.fhirResource.id!!
        assertModelExpectations(record.fhirResource, Method.CREATE)
    }

    @Test
    fun t02_createRecords_shouldReturn_createdRecords() {
        lateinit var createResult: CreateResult<T>

        //given
        val model1 = getTestModel()
        val model2 = getTestModel()

        //when
        client.createRecords(listOf(model1, model2), object : TestResultListener<CreateResult<T>>() {
            override fun onSuccess(result: CreateResult<T>) {
                createResult = result
                latch.countDown()

            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Create records failed", requestSuccessful)
        assertEquals(2, createResult.successfulOperations.size)
        assertTrue(createResult.failedOperations.isEmpty())

        createResult.successfulOperations.map {
            assertRecordExpectations(it)
            assertNotNull(it.fhirResource.id)
            recordIds.add(it.fhirResource.id!!)
            assertModelExpectations(it.fhirResource, Method.BATCH_CREATE)
        }
    }

    @Test
    fun t03_countRecords_shouldReturn_recordCount() {
        var modelCount: Int = -1

        //when
        client.countRecords(getModelClass(), object : TestResultListener<Int>() {
            override fun onSuccess(count: Int) {
                modelCount = count
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertNotEquals(-1, modelCount, "Count records failed")
        assertEquals(3, modelCount)
    }

    @Test
    fun t04_countAllRecords_shouldReturn_recordCount() {
        var modelCount: Int = -1

        //when
        client.countRecords(null, object : TestResultListener<Int>() {
            override fun onSuccess(count: Int) {
                modelCount = count
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertNotEquals(-1, modelCount, "Count all records failed")
        assertEquals(3, modelCount)
    }

    @Test
    fun t05_fetchRecord_shouldReturn_fetchedRecord() {
        //given
        assertNotNull("recordId expected", recordId)
        lateinit var record: Record<T>

        //when
        client.fetchRecord(recordId, object : TestResultListener<Record<T>>() {
            override fun onSuccess(r: Record<T>) {
                record = r
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Fetch record failed", requestSuccessful)
        assertRecordExpectations(record)
        assertModelExpectations(record.fhirResource, Method.FETCH)
    }

    @Test
    fun t06_fetchRecords_shouldReturn_fetchedRecords() {
        //given
        assertNotNull("recordId expected", recordId)
        lateinit var fetchResult: FetchResult<T>

        //when
        client.fetchRecords(listOf(recordId, recordId), object : TestResultListener<FetchResult<T>>() {
            override fun onSuccess(result: FetchResult<T>) {
                fetchResult = result
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Fetch records failed", requestSuccessful)
        assertEquals(2, fetchResult.successfulFetches.size)
        assertTrue(fetchResult.failedFetches.isEmpty())
        fetchResult.successfulFetches.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.FETCH_BY_ID)
        }
    }

    @Test
    fun t07_fetchRecordsByType_shouldReturn_fetchedRecords() {
        //given
        assertNotNull("recordId expected", recordId)
        lateinit var fetchedRecords: List<Record<T>>

        //when
        client.fetchRecords(
                getModelClass(),
                LocalDate.now().minusYears(10),
                LocalDate.now(),
                1000,
                0,
                object : TestResultListener<List<Record<T>>>() {
                    override fun onSuccess(records: List<Record<T>>) {
                        fetchedRecords = records
                        latch.countDown()
                    }
                })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Fetch records by type failed", requestSuccessful)
        assertEquals(3, fetchedRecords.size)
        fetchedRecords.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.FETCH_BY_TYPE)
        }
    }

    @Test
    fun t08_updateRecord_shouldReturn_updatedRecord() {
        //given
        assertNotNull("recordId expected", recordId)
        prepareModel(model, Method.UPDATE)
        lateinit var updatedRecord: Record<T>

        //when
        client.updateRecord(model, object : TestResultListener<Record<T>>() {
            override fun onSuccess(record: Record<T>) {
                updatedRecord = record
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Update record failed", requestSuccessful)
        assertRecordExpectations(updatedRecord)
        assertModelExpectations(updatedRecord.fhirResource, Method.UPDATE)
    }

    @Test
    fun t09_updateRecords_shouldReturn_updatedRecords() {
        //given
        assertNotNull("recordId expected", recordId)
        lateinit var updateResult: UpdateResult<T>

        val model1 = getTestModel()
        val model2 = getTestModel()
        prepareModel(model1, Method.BATCH_UPDATE, 0)
        prepareModel(model2, Method.BATCH_UPDATE, 1)

        //when
        client.updateRecords(listOf(model1, model2), object : TestResultListener<UpdateResult<T>>() {
            override fun onSuccess(result: UpdateResult<T>) {
                updateResult = result
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Update records failed", requestSuccessful)
        assertEquals(2, updateResult.successfulUpdates.size)
        assertTrue(updateResult.failedUpdates.isEmpty())

        var cnt = 0
        updateResult.successfulUpdates.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.BATCH_UPDATE, cnt++)
        }
    }

    @Test
    fun t10_downloadRecord_shouldReturn_downloadedRecord() {
        //given
        assertNotNull("recordId expected", recordId)
        lateinit var downloadedRecord: Record<T>

        //when
        client.downloadRecord(recordId, object : TestResultListener<Record<T>>() {
            override fun onSuccess(record: Record<T>) {
                downloadedRecord = record
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Download record failed", requestSuccessful)
        assertRecordExpectations(downloadedRecord)
        assertModelExpectations(downloadedRecord.fhirResource, Method.DOWNLOAD)
    }

    @Test
    fun t11_downloadRecords_shouldReturn_downloadedRecord() {
        //given
        assertNotNull("recordId expected", recordId)
        lateinit var downloadResult: DownloadResult<T>

        //when
        client.downloadRecords(listOf(recordId, recordId), object : TestResultListener<DownloadResult<T>>() {
            override fun onSuccess(result: DownloadResult<T>) {
                downloadResult = result
                latch.countDown()
            }
        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Download records failed", requestSuccessful)
        assertEquals(2, downloadResult.successfulDownloads.size)
        assertTrue(downloadResult.failedDownloads.isEmpty())
        downloadResult.successfulDownloads.map {
            assertRecordExpectations(it)
            assertModelExpectations(it.fhirResource, Method.BATCH_DOWNLOAD)
        }
    }

    @Test
    fun t12_deleteRecord_shouldDeleteRecord() {
        //given
        assertNotNull("recordId expected", recordId)

        //when
        client.deleteRecord(recordId, object : Callback {
            override fun onSuccess() {
                latch.countDown()
            }

            override fun onError(exception: HCException) {
                exception.printStackTrace()
                requestSuccessful = false
                latch.countDown()
            }

        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Delete record failed", requestSuccessful)
    }

    @Test
    fun t13_deleteRecords_shouldDeleteRecords() {
        //given
        assertEquals("recordIds expected", 2, recordIds.size)
        lateinit var deleteResult: DeleteResult

        //when
        client.deleteRecords(recordIds, object : TestResultListener<DeleteResult>() {
            override fun onSuccess(result: DeleteResult) {
                deleteResult = result
                latch.countDown()
            }

        })
        latch.await(TIMEOUT, TimeUnit.SECONDS)

        //then
        assertTrue("Delete records failed", requestSuccessful)
        assertEquals(2, deleteResult.successfulDeletes.size)
        assertTrue(deleteResult.failedDeletes.isEmpty())
        for (i in 0..1) assertEquals(recordIds[i], deleteResult.successfulDeletes[i])
    }
}
