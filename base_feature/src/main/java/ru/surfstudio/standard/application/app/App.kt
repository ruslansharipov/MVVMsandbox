package ru.surfstudio.standard.application.app

import android.app.Application
import ru.surfstudio.android.activity.holder.ActiveActivityHolder
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.navigation.animation.DefaultAnimations
import ru.surfstudio.android.navigation.animation.resource.EmptyResourceAnimations
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.utilktx.ktx.ui.activity.ActivityLifecycleListener
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
        initDefaultAnimations()
        registerLogCallbacks()
        registerDiCallbacks()
        registerNavigationCallbacks()

        initDebugScreen()
    }

    private fun registerLogCallbacks() {
        registerActivityLifecycleCallbacks(appComponent.logLifecycleCallbacks())
    }

    private fun registerNavigationCallbacks() {
        registerActivityLifecycleCallbacks(appComponent.activityNavigationProvider())
    }

    private fun registerDiCallbacks() {
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

    private fun initDebugScreen() {
        val activeActivityHolder = ActiveActivityHolder()
        registerActivityLifecycleCallbacks(
                ActivityLifecycleListener(
                        onActivityResumed = { activity ->
                            activeActivityHolder.activity = activity
                        },
                        onActivityPaused = {
                            activeActivityHolder.clearActivity()
                        }
                )
        )
    }
}