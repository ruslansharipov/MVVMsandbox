package ru.surfstudio.android.mvvmsandbox.app.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.surfstudio.android.mvvmsandbox.network.call.adapter.NetworkErrorConverter
import ru.surfstudio.android.mvvmsandbox.network.call.adapter.CallAdapterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun providesErrorConverter(): NetworkErrorConverter {
        return NetworkErrorConverter()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, errorConverter: NetworkErrorConverter): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://r2.mocker.surfstudio.ru/")
                .client(okHttpClient)
                .addCallAdapterFactory(CallAdapterFactory(errorConverter))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesOkhttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
    }
}