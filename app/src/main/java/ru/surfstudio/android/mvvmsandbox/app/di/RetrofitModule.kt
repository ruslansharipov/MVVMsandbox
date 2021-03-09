package ru.surfstudio.android.mvvmsandbox.app.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://upstage.rivegauche.ru/rg/v1/newRG/")
            .client(OkHttpClient())
            .build()
    }
}