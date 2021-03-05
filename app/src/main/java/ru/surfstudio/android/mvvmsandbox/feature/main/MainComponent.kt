package ru.surfstudio.android.mvvmsandbox.feature.main

import dagger.Component
import ru.surfstudio.android.mvvmsandbox.activity.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope

@ScreenScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [
        MainActivityModule::class
    ]
)
interface MainComponent {

    fun inject(view: MainActivity)
}