package ai.labiba.labibavoiceassistant.network

import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVA
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var mClient: OkHttpClient? = null
    private var mRetro: Retrofit? = null
    private var mApiInterface: LabibaApiRetrofitInterface? = null

    private fun getOkHttpClient(): OkHttpClient {
        return if (mClient != null) {
            mClient!!
        } else {
            mClient = getClient(getLogging())
            mClient ?: getClient(getLogging())
        }
    }

    private fun getRetrofit(): Retrofit {
        return if (mRetro != null) {
            mRetro!!
        } else {
            mRetro = Retrofit.Builder()
                .baseUrl(LabibaVA.urls.generalBaseUrl)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()

            mRetro!!
        }
    }

    fun getApiInterface():LabibaApiRetrofitInterface{
        return if (mApiInterface != null) {
            mApiInterface!!
        } else {
            mApiInterface = getRetrofit().create(LabibaApiRetrofitInterface::class.java)
            mApiInterface!!
        }
    }

    private fun getLogging() = HttpLoggingInterceptor {
        Log.i("ProfileLog", "provideHttpLoggingInterceptor: $it")
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getClient(HttpLogging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLogging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

}