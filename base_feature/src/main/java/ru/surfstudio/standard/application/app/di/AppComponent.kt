package ru.surfstudio.standard.application.app.di

import dagger.Component
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.application.migration.di.MigrationModule
import ru.surfstudio.standard.application.navigation.di.NavigationModule
import ru.surfstudio.standard.application.network.di.NetworkModule
import ru.surfstudio.standard.application.network.di.OkHttpModule
import ru.surfstudio.standard.application.storage.di.SharedPrefModule

@PerApplication
@Component(
        modules = [
                AppModule::class,
                NavigationModule::class,
                NetworkModule::class,
                OkHttpModule::class,
                MigrationModule::class,
                SharedPrefModule::class
// TODO добавить недостающие модули
        ]
)
interface AppComponent : AppProxyDependencies
