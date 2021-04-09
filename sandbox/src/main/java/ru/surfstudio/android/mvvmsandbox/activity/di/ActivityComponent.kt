package ru.surfstudio.android.mvvmsandbox.activity.di

import dagger.Component
import ru.surfstudio.android.mvvmsandbox.app.di.AppComponent
import ru.surfstudio.android.mvvmsandbox.app.di.AppProxyDependencies

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent: AppProxyDependencies, ActivityProxyDependencies