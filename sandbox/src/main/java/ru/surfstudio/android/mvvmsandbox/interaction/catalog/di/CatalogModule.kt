package ru.surfstudio.android.mvvmsandbox.interaction.catalog.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.mvvmsandbox.interaction.catalog.CatalogApi
import ru.surfstudio.android.dagger.scope.PerApplication

@Module
class CatalogModule {

    @Provides
    @PerApplication
    fun provideCatalogApi(retrofit: Retrofit): CatalogApi {
        return retrofit.create(CatalogApi::class.java)
    }
}