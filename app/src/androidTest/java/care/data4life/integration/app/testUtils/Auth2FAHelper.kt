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

import android.content.Context
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.widget.FitWindowsLinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import care.data4life.integration.app.testUtils.Auth2FAHelper.fetchCurrent2faCode
import com.google.gson.annotations.SerializedName
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.connection.ConnectInterceptor.intercept
import okhttp3.internal.waitMillis
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.lang.Thread.sleep
import java.util.*
import kotlin.collections.ArrayList

interface TwillioService {

    @GET("2010-04-01/Accounts/$ACCOUNT_SID/Messages.json")
    fun get2FACode(
            @Query("dateSent") date: String,
            @Query("to") phoneNumber: String,
            @Query("pageSize") page: Int
    ) : Call<ListMessage>

    companion object {
        const val BASE_URL = "https://api.twilio.com"
        const val ACCOUNT_SID = "ACcfd6d6a012cc5076c3bc3aa99d1f98a8"
        const val AUTH_SID = "SKaf8a5eaa4e3e5fd5d01b3daff4060684"
        const val AUTH_TOKEN = "JVExGoQKNEusgWZx7BRbBMqWu7orXWmM"
    }

}

class Message {

    @SerializedName("body")
    var body: String? = null
    @SerializedName("error_code")
    var error_code: String? = null
    @SerializedName("from")
    var from: String? = null
    @SerializedName("to")
    var to: String? = null
}


class ListMessage {
    @SerializedName("messages")
    var messages: List<Message>? = null
    @SerializedName("first_page_uri")
    var first_page_uri: String? = null
    @SerializedName("end")
    var end: Int? = null

}

class BasicAuthInterceptor(user: String, password: String) : Interceptor {

    private val credentials: String = Credentials.basic(user, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response{
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}

object Auth2FAHelper {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)




    fun initTwillioService(): TwillioService {
        val twillioService: TwillioService

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(TwillioService.AUTH_SID, TwillioService.AUTH_TOKEN))
                    .build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(TwillioService.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


            twillioService = retrofit.create(TwillioService::class.java)

        return twillioService
    }


    fun fetchLatest2FACode(phoneNumber: String, date: String): ListMessage?{
        val call: Call<ListMessage> = initTwillioService().get2FACode(date, phoneNumber, 1)
        var messages = call.execute().body()
        return messages
    }

    fun fetchCurrent2faCode(phoneNumber: String): String? {
        val date = dateFormatter.format(LocalDate.now())
        val message = fetchLatest2FACode(phoneNumber, date)?.messages?.get(0)?.body

        return message
    }


    fun extractVerificationCode(text: String?): String? {
        var verificationCode = text
        if(text!!.isNotEmpty())
            verificationCode = text.substring(text.length-6,text.length)
        return verificationCode
    }

}



class Auth2FAHelperTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        AndroidThreeTen.init(InstrumentationRegistry.getInstrumentation().targetContext)
        instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun tryIt() {
        sleep(5000)
        //Auth2FAHelper.extractVerificationCode(fetchCurrent2faCode())
    }

}
