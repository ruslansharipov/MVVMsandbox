package ru.surfstudio.android.mvvmsandbox.app.di

import dagger.Component
import ru.surfstudio.android.mvvmsandbox.navigation.di.NavigationModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class
    ]
)
interface AppComponent : AppProxyDependencies
