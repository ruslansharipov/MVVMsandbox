package ru.surfstudio.android.mvvmsandbox.interaction.cities.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.mvvmsandbox.interaction.cities.CitiesApi
import javax.inject.Singleton

@Module
class CitiesModule {

    @Provides
    @Singleton
    fun providesCitiesApi(retrofit: Retrofit) : CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }
}