package ru.surfstudio.standard.application.app

import android.app.Application
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.surfstudio.standard.application.app.di.AppComponent
import ru.surfstudio.standard.application.app.di.AppModule
import ru.surfstudio.standard.application.app.di.DaggerAppComponent
import ru.surfstudio.standard.application.app.di.callbacks.DiActivityCallbacks
import ru.surfstudio.standard.application.logger.strategies.local.TimberLoggingStrategy

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        initLogger()
        initDiCallbacks()
        initNavigationCallbacks()
    }

    private fun initNavigationCallbacks() {
        registerActivityLifecycleCallbacks(
                appComponent.activityNavigationProvider() as ActivityNavigationProviderCallbacks
        )
    }

    private fun initDiCallbacks() {
        registerActivityLifecycleCallbacks(DiActivityCallbacks())
    }

    private fun initLogger() {
        Logger.addLoggingStrategy(TimberLoggingStrategy())
    }
}