package ru.surfstudio.android.mvvmsandbox.app.di

import dagger.Component
import ru.surfstudio.android.mvvmsandbox.interaction.catalog.di.CatalogModule
import ru.surfstudio.android.mvvmsandbox.interaction.cities.di.CitiesModule
import ru.surfstudio.android.mvvmsandbox.navigation.di.NavigationModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        NetworkModule::class,
        CatalogModule::class,
        CitiesModule::class
    ]
)
interface AppComponent : AppProxyDependencies
