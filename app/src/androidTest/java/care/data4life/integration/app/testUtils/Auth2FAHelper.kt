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

import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.Credentials
import okhttp3.Request
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.util.*

interface TwillioService {

    @GET("2010-04-01/Accounts/{${ACCOUNT_SID}}/Messages.json")
    fun fetchLatest2FACode(
            @Header("Authorization") authkey: String,
            @Query("dateSent") date: String,
            @Query("to") phoneNumber: String,
            @Query( "PageSize") pageSize: Int = 1
    ) : String

    companion object {
        const val BASE_URL = "https://api.twilio.com"
        const val ACCOUNT_SID = "AC45c8932053f153ce647e71ba04081d13"
        const val AUTH_SID = "SKaf8a5eaa4e3e5fd5d01b3daff4060684"
        const val AUTH_TOKEN = "JVExGoQKNEusgWZx7BRbBMqWu7orXWmM"
    }

}

object Auth2FAHelper {

    const val AUTH_PHONE_NUMBER = "+19292544521"


    private val credential = Credentials.basic(TwillioService.AUTH_SID, TwillioService.AUTH_TOKEN)

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)


    private val twillioService: TwillioService


    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(TwillioService.BASE_URL)
                .build()

        twillioService = retrofit.create(TwillioService::class.java)
    }


    fun fetchCurrent2faCode(): String {
        val date = dateFormatter.format(LocalDate.now())

        val code = twillioService.fetchLatest2FACode(credential, date, AUTH_PHONE_NUMBER)

        return ""
    }


//    fun extractVerificationCode(text: String): String {
//
//    }

}

class Auth2FAHelperTest {

    @Before
    fun setup() {
        AndroidThreeTen.init(InstrumentationRegistry.getInstrumentation().targetContext)
    }


    @Test
    fun tryIt() {
        Auth2FAHelper.fetchCurrent2faCode()
    }

}
