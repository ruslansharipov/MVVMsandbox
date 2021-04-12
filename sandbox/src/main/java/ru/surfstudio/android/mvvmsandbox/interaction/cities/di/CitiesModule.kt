package ru.surfstudio.android.mvvmsandbox.interaction.cities.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.mvvmsandbox.interaction.cities.CitiesApi
import ru.surfstudio.android.dagger.scope.PerApplication

@Module
class CitiesModule {

    @Provides
    @PerApplication
    fun providesCitiesApi(retrofit: Retrofit) : CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }
}