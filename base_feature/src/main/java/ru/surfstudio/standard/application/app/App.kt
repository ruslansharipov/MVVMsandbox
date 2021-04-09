package ru.surfstudio.standard.application.app

import android.app.Application
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.surfstudio.standard.application.app.di.AppComponent
import ru.surfstudio.standard.application.app.di.AppModule
import ru.surfstudio.standard.application.app.di.DaggerAppComponent
import ru.surfstudio.standard.application.app.di.callbacks.DiActivityCallbacks

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        registerActivityLifecycleCallbacks(DiActivityCallbacks())
        registerActivityLifecycleCallbacks(
            appComponent.activityNavigationProvider() as ActivityNavigationProviderCallbacks
        )
    }
}