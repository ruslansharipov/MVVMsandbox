package ru.surfstudio.android.mvvmsandbox.feature.main

import dagger.Component
import ru.surfstudio.android.mvvmsandbox.activity.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule

@ScreenScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [
        MainActivityModule::class,
        ViewModelFactoryModule::class,
        MainActivityViewModelModule::class
    ]
)
interface MainComponent {

    fun inject(view: MainActivity)
}