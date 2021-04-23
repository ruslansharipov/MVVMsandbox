package ru.surfstudio.standard.application.network.di

import android.app.DownloadManager
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.surfstudio.standard.i_network.converter.gson.ResponseTypeAdapterFactory
import ru.surfstudio.standard.i_network.converter.gson.SafeConverterFactory
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.template.base_feature.BuildConfig
import ru.surfstudio.standard.i_network.network.BaseUrl
import ru.surfstudio.standard.i_network.BASE_API_URL
import ru.surfstudio.standard.i_network.TEST_API_URL
import ru.surfstudio.standard.i_network.network.adapter.CallAdapterFactory
import ru.surfstudio.standard.i_network.network.adapter.NetworkErrorConverter

@Module
class NetworkModule {

    companion object {
        private const val HTTP_LOG_TAG = "OkHttp"
    }

    @Provides
    @PerApplication
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapterFactory,
        gson: Gson,
        apiUrl: BaseUrl
    ): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(apiUrl.toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(callAdapterFactory)
                .build()
    }

    @Provides
    @PerApplication
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapterFactory(ResponseTypeAdapterFactory(SafeConverterFactory()))
                .create()
    }

    @Provides
    @PerApplication
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) = Logger.d("$HTTP_LOG_TAG $message")
        }
        return HttpLoggingInterceptor(logger).apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    @Provides
    @PerApplication
    internal fun provideDownloadManager(context: Context): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    @PerApplication
    internal fun provideCallAdapterFactory(errorConverter: NetworkErrorConverter): CallAdapterFactory {
        return CallAdapterFactory(errorConverter)
    }


    @Provides
    @PerApplication
    internal fun provideErrorConverter(): NetworkErrorConverter {
        return NetworkErrorConverter()
    }

    @Provides
    @PerApplication
    internal fun provideBaseUrl(): BaseUrl {
        return BaseUrl(TEST_API_URL, null)
    }
}