package ru.surfstudio.standard.f_splash

import android.os.Bundle
import ru.surfstudio.android.notification.ui.notification.PushHandlingActivity
import ru.surfstudio.android.template.f_splash.R
import ru.surfstudio.standard.f_splash.di.SplashScreenConfigurator
import ru.surfstudio.standard.ui.configuration.configurator.ActivityConfigurator
import ru.surfstudio.standard.ui.mvvm.view.BaseMVVMActivityView

internal class SplashActivityView : BaseMVVMActivityView(), PushHandlingActivity {

    override val name: String = "SplashActivityView"

    override fun createConfigurator(): ActivityConfigurator = SplashScreenConfigurator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}