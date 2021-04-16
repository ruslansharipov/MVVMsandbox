package ru.surfstudio.standard.application.app.di

import dagger.Component
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.surfstudio.standard.application.app.callbacks.di.DiActivityCallbacks
import ru.surfstudio.standard.application.app.callbacks.log.LifecycleLogActivityCallbacks
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
interface AppComponent : AppProxyDependencies {

    //провайдим эти колбеки в AppComponent, а не в AppProxyDependencies
    // так как дочерние компоненты не должны иметь к ним доступ

    fun activityNavigationProvider(): ActivityNavigationProviderCallbacks
    fun logLifecycleCallbacks(): LifecycleLogActivityCallbacks
    fun diCallbacks(): DiActivityCallbacks
}
