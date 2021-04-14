package ru.surfstudio.standard.application.app

import android.app.Application
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.navigation.animation.DefaultAnimations
import ru.surfstudio.android.navigation.animation.resource.EmptyResourceAnimations
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.surfstudio.standard.application.app.di.AppComponent
import ru.surfstudio.standard.application.app.di.AppModule
import ru.surfstudio.standard.application.app.di.DaggerAppComponent
import ru.surfstudio.standard.application.logger.strategies.local.TimberLoggingStrategy
import ru.surfstudio.standard.ui.animation.SlideAnimations

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        initLogger()
        initLogCallbacks()
        initDiCallbacks()
        initNavigationCallbacks()
        initDefaultAnimations()
    }

    private fun initLogCallbacks() {
        registerActivityLifecycleCallbacks(appComponent.logLifecycleCallbacks())
    }

    private fun initNavigationCallbacks() {
        registerActivityLifecycleCallbacks(
                appComponent.activityNavigationProvider() as ActivityNavigationProviderCallbacks
        )
    }

    private fun initDiCallbacks() {
        registerActivityLifecycleCallbacks(appComponent.diCallbacks())
    }

    private fun initLogger() {
        Logger.addLoggingStrategy(TimberLoggingStrategy())
    }

    private fun initDefaultAnimations() {
        DefaultAnimations.fragment = SlideAnimations
        DefaultAnimations.activity = SlideAnimations
        DefaultAnimations.tab = EmptyResourceAnimations
    }
}