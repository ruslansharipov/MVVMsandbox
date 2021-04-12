package ru.surfstudio.standard.f_splash

import ru.surfstudio.android.notification.ui.notification.PushHandlingActivity
import ru.surfstudio.standard.f_splash.di.SplashScreenConfigurator
import ru.surfstudio.standard.ui.configurator.Configurator
import ru.surfstudio.standard.ui.mvvm.view.BaseMVVMActivityView

internal class SplashActivityView : BaseMVVMActivityView(), PushHandlingActivity {

    override fun createConfigurator(): Configurator = SplashScreenConfigurator(application, viewModelStore, intent)
}