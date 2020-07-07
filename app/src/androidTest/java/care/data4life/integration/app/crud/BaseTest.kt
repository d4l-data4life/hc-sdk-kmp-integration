/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.crud

import androidx.test.rule.ActivityTestRule
import care.data4life.fhir.stu3.model.DomainResource
import care.data4life.integration.app.MainActivity
import care.data4life.integration.app.page.HomePage
import care.data4life.integration.app.page.WelcomePage
import care.data4life.integration.app.testUtils.NetworkUtil
import care.data4life.integration.app.testUtils.TestConfigLoader
import care.data4life.integration.app.testUtils.deleteAllRecords
import care.data4life.sdk.Data4LifeClient
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.Callback
import care.data4life.sdk.listener.ResultListener
import care.data4life.sdk.model.*
import com.jakewharton.threetenabp.AndroidThreeTen
import org.junit.*
import org.junit.Assert.*
import org.junit.Assume.assumeTrue
import org.junit.runners.MethodSorters
import org.threeten.bp.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertNotEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //test order is important for successful completion!
abstract class BaseTest<T : DomainResource> {

    abstract fun getTestClass(): Class<T>

    abstract fun getModel(method: Method, index: Int = -1): T

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
        private val TIMEOUT = 10L
        private var setupDone = false
        private var isNetConnected: Boolean = false
        private lateinit var latch: CountDownLatch
        private var requestSuccessful = true
        private val rule = ActivityTestRule(MainActivity::class.java, false, false)
        private lateinit var activity: MainActivity
        private lateinit var homePage: HomePage

        @JvmStatic
        protected lateinit var recordId: String

        @JvmStatic
        protected var recordIds = mutableListOf<String>()


        //SUT
        @JvmStatic
        protected lateinit var client: Data4LifeClient


        @BeforeClass
        @JvmStatic
        fun suiteSetup() {
            isNetConnected = NetworkUtil.isOnline()
            assumeTrue("Internet connection required", isNetConnected)

            activity = rule.launchActivity(null)
            AndroidThreeTen.init(activity.application)
            client = Data4LifeClient.getInstance()

            val user = TestConfigLoader.load().user

            homePage = WelcomePage()
                    .isVisible()
                    .openLoginPage()
                    .doLogin(user)
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
            recordId = ""
            recordIds.clear()
            setupDone = false
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

                override fun onError(exception: D4LException) {
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
        requestSuccessful = true

        if (setupDone) return
        else {
            setupDone = true
            client.deleteAllRecords(getTestClass()) //run only once before all the tests
        }
    }

    abstract inner class TestResultListener<V> : ResultListener<V> {
        override fun onError(exception: D4LException) {
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
        client.createRecord(getModel(Method.CREATE), object : TestResultListener<Record<T>>() {
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
        val model1 = getModel(Method.BATCH_CREATE)
        val model2 = getModel(Method.BATCH_CREATE)

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
        client.countRecords(getTestClass(), object : TestResultListener<Int>() {
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
                getTestClass(),
                null,
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
        lateinit var updatedRecord: Record<T>

        //when
        client.updateRecord(getModel(Method.UPDATE), object : TestResultListener<Record<T>>() {
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

        val model1 = getModel(Method.BATCH_UPDATE, 0)
        val model2 = getModel(Method.BATCH_UPDATE, 1)

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

            override fun onError(exception: D4LException) {
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
