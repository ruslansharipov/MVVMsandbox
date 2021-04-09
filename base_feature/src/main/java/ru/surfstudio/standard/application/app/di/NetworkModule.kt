package ru.surfstudio.standard.application.app.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.template.base_feature.BuildConfig
import ru.surfstudio.standard.i_network.converter.gson.ResponseTypeAdapterFactory
import ru.surfstudio.standard.i_network.converter.gson.SafeConverterFactory
import ru.surfstudio.standard.i_network.network.adapter.CallAdapterFactory
import ru.surfstudio.standard.i_network.network.adapter.NetworkErrorConverter
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val HTTP_LOG_TAG = "OkHttp"
        private const val NETWORK_TIMEOUT = 30L // sec
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapterFactory(ResponseTypeAdapterFactory(SafeConverterFactory()))
                .create()
    }

    @Provides
    @Singleton
    fun providesErrorConverter(): NetworkErrorConverter {
        return NetworkErrorConverter()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
            errorConverter: NetworkErrorConverter,
            gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://r2.mocker.surfstudio.ru/")
                .client(okHttpClient)
                .addCallAdapterFactory(CallAdapterFactory(errorConverter))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = object: HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.d("$HTTP_LOG_TAG $message")
            }
        }
        return HttpLoggingInterceptor(logger).apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    @Provides
    @Singleton
    fun providesOkhttp(
            loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
//                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }
}