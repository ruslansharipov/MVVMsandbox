package ru.surfstudio.android.mvvmsandbox.app

import android.app.Application
import ru.surfstudio.android.mvvmsandbox.app.di.AppComponent
import ru.surfstudio.android.mvvmsandbox.app.di.AppModule
import ru.surfstudio.android.mvvmsandbox.app.di.DaggerAppComponent
import ru.surfstudio.android.mvvmsandbox.app.di.callbacks.DiActivityCallbacks
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks

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