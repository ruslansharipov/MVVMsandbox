package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.mvvmsandbox.activity.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.activity.ActivityModule
import ru.surfstudio.android.mvvmsandbox.activity.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.mvvmsandbox.feature.di.*
import ru.surfstudio.android.mvvmsandbox.view_model.ProviderViewModelFactory
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule
import javax.inject.Provider

internal class MainScreenConfigurator {

    @ScreenScope
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [
            MainActivityModule::class,
            ViewModelFactoryModule::class,
            ViewModelStoreModule::class
        ]
    )
    internal interface MainComponent {
        fun inject(view: MainActivity)
    }

    @Module
    internal class MainActivityModule(route: MainScreenRoute) :
        CustomScreenModule<MainScreenRoute>(route) {

        @Provides
        fun provideViewModel(
            viewModelStore: ViewModelStore,
            provider: Provider<MainViewModel>,
            route: MainScreenRoute
        ): IMainViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                route.getId(),
                MainViewModel::class.java
            )
        }
    }

    fun inject(activity: MainActivity) {
        val activityComponent = DaggerActivityComponent.builder()
            .appComponent((activity.application as App).appComponent)
            .activityModule(ActivityModule())
            .build()

        DaggerMainScreenConfigurator_MainComponent.builder()
            .activityComponent(activityComponent)
            .viewModelStoreModule(ViewModelStoreModule(activity.viewModelStore))
            .mainActivityModule(MainActivityModule(MainScreenRoute(activity.intent)))
            .build()
            .inject(activity)
    }
}