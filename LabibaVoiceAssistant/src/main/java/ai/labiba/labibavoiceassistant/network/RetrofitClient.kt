package ai.labiba.labibavoiceassistant.network

import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVA
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import android.os.Build
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {

    private var mClient: OkHttpClient? = null
    private var mRetro: Retrofit? = null
    private var mApiInterface: LabibaApiRetrofitInterface? = null

    private fun getOkHttpClient(): OkHttpClient {
        return if (mClient != null) {
            mClient!!
        } else {

            if(LabibaVAInternal.labibaVaTheme.themeSettings.useUnsafeOkHttpClient){
                mClient = getUnsafeOkHttpClient()
                mClient ?: getUnsafeOkHttpClient()
            }else{
                mClient = getClient(getLogging())
                mClient ?: getClient(getLogging())
            }
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
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()


    fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains

            val trustManager: X509TrustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOf<X509Certificate?>()
                }
            }
            val trustAllCerts = arrayOf<TrustManager>(trustManager)

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")


            //            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.getSocketFactory()

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                sslSocketFactory,
                (trustAllCerts[0] as javax.net.ssl.X509TrustManager?)!!
            )
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)


            builder.addInterceptor(logging)
            builder.readTimeout(
                2,
                TimeUnit.MINUTES
            )
            builder.writeTimeout(
                2,
                TimeUnit.MINUTES
            )
            builder.connectTimeout(
                2,
                TimeUnit.MINUTES
            )

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}