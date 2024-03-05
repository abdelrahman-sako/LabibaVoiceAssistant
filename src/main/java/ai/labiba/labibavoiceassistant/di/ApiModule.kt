package ai.labiba.labibavoiceassistant.di

import ai.labiba.labibavoiceassistant.network.ApiRepository
import ai.labiba.labibavoiceassistant.network.LabibaApiRetrofitInterface
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        Log.i("LabibaVoiceAssistant", "provideHttpLoggingInterceptor: $it")
    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
//        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(HttpLogging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLogging)
            .connectTimeout(1L, TimeUnit.MINUTES)
            .writeTimeout(1L, TimeUnit.MINUTES)
            .readTimeout(1L, TimeUnit.MINUTES)
            .build()/*.unSafePassBy()*/


    @Singleton
    @Provides
    fun provideLazyApiRepository(apiRepository: ApiRepository): Lazy<ApiRepository> = lazy { apiRepository }



    @Suppress("DEPRECATION")
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("https://google.com")
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): LabibaApiRetrofitInterface =
        retrofit.create(LabibaApiRetrofitInterface::class.java)


}