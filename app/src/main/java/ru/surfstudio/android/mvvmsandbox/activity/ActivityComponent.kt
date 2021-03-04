package ru.surfstudio.android.mvvmsandbox.activity

import dagger.Component
import ru.surfstudio.android.mvvmsandbox.app.di.AppComponent
import ru.surfstudio.android.mvvmsandbox.app.di.AppProxyDependencies
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class, ViewModelFactoryModule::class]
)
interface ActivityComponent: AppProxyDependencies, ActivityProxyDependencies