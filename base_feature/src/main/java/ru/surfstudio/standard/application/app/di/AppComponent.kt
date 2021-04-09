package ru.surfstudio.standard.application.app.di

import dagger.Component
import ru.surfstudio.standard.application.navigation.di.NavigationModule
import ru.surfstudio.standard.application.network.di.OkHttpModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        NetworkModule::class,
        OkHttpModule::class
// TODO добавить недостающие модули
    ]
)
interface AppComponent : AppProxyDependencies
