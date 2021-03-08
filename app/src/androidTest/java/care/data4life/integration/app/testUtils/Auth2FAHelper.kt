package care.data4life.integration.app.testUtils

import care.data4life.integration.app.testUtils.Auth2FAHelper.config
import care.data4life.integration.app.testUtils.SinchService.Companion.AUTH_TOKEN
import care.data4life.integration.app.testUtils.SinchService.Companion.SERVICE_PLAN
import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

interface SinchService {
    @GET("{service_plan_id}/inbounds")
    fun get2FACode(
            @Path("service_plan_id") servicePlanId: String,
            @Query("start_date") startDate: String,
            @Query("to") phoneNumber: String,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int
    ): retrofit2.Call<ListMessageSinch>

    companion object {
        const val BASE_URL = "https://eu.sms.api.sinch.com/xms/v1/"
        val AUTH_TOKEN = config.authToken
        val SERVICE_PLAN = config.servicePlanId
    }
}

class MessageSinch {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("body")
    var body: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("from")
    var from: String? = null

    @SerializedName("to")
    var to: String? = null
}

class ListMessageSinch {
    @SerializedName("inbounds")
    var messages: List<MessageSinch>? = null

    @SerializedName("page")
    var page: Int? = null

    @SerializedName("page_size")
    var pageSize: Int? = null

    @SerializedName("count")
    var count: Int? = null
}
//endregion

public class BasicSAuthInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $AUTH_TOKEN")
                .build()
        return chain.proceed(authenticatedRequest)
    }
}

object Auth2FAHelper {
    const val TIMEOUT_SHORT = 1000 * 5L

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    val config = TestConfigLoader.load().sinch

    fun initSinchService(): SinchService {
        val sinchService: SinchService
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(BasicSAuthInterceptor())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(SinchService.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        sinchService = retrofit.create(SinchService::class.java)
        return sinchService
    }

    fun fetchLatest2FACode(phoneNumber: String, date: String): ListMessageSinch? {
        val call = initSinchService().get2FACode(SERVICE_PLAN, date, phoneNumber, 0, 30)
        return call.execute().body()
    }

    fun fetchCurrent2faCode(phoneNumber: String): String? {
        Thread.sleep(TIMEOUT_SHORT)
        val date = dateFormatter.format(LocalDate.now())
        return fetchLatest2FACode(phoneNumber, date)?.messages?.get(0)?.body
    }

    fun extractVerificationCode(text: String?): String? {
        var verificationCode = text
        if (text!!.isNotEmpty())
            verificationCode = text.substring(text.length - 6, text.length)
        return verificationCode
    }

}

