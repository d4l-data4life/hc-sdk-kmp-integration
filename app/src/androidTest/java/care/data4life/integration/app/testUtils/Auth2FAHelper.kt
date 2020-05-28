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

import care.data4life.integration.app.testUtils.TwillioService.Companion.WRONG_PIN
import com.google.gson.annotations.SerializedName
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.*

interface TwillioService {

    @GET("2010-04-01/Accounts/$ACCOUNT_SID/Messages.json")
    fun get2FACode(
            @Query("dateSent") date: String,
            @Query("to") phoneNumber: String,
            @Query("pageSize") page: Int
    ): Call<ListMessage>

    companion object {
        const val BASE_URL = "https://api.twilio.com"
        const val ACCOUNT_SID = "ACcfd6d6a012cc5076c3bc3aa99d1f98a8"
        const val AUTH_SID = "SKaf8a5eaa4e3e5fd5d01b3daff4060684"
        const val AUTH_TOKEN = "JVExGoQKNEusgWZx7BRbBMqWu7orXWmM"
        const val WRONG_PIN = "000000"
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
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}

object Auth2FAHelper {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)


    private fun initTwillioService(): TwillioService {
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

    private fun fetchLatest2FACode(phoneNumber: String, date: String): ListMessage? {
        val call: Call<ListMessage> = initTwillioService().get2FACode(date, phoneNumber, 1)
        return call.execute().body()
    }

    fun fetchCurrent2faCode(phoneNumber: String): String {
        val date = dateFormatter.format(LocalDate.now())
        val message = fetchLatest2FACode(phoneNumber, date)?.messages?.get(0)?.body
        return message?.substring(
                startIndex = message.length - 6,
                endIndex = message.length
        ) ?: WRONG_PIN
    }

}

