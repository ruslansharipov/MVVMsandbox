package ru.surfstudio.android.mvvmsandbox.feature.main

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityModule
import ru.surfstudio.android.mvvmsandbox.activity.di.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.mvvmsandbox.configurator.Configurator
import ru.surfstudio.android.mvvmsandbox.configurator.ScreenComponent
import ru.surfstudio.android.mvvmsandbox.feature.di.*
import ru.surfstudio.android.mvvmsandbox.view_model.ProviderViewModelFactory
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule
import javax.inject.Provider

class MainScreenConfigurator(
        private val application: Application,
        private val viewModelStore: ViewModelStore,
        private val intent: Intent
) : Configurator {

    @ScreenScope
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                MainActivityModule::class,
                ViewModelFactoryModule::class,
                ViewModelStoreModule::class
            ]
    )
    internal interface MainComponent : ScreenComponent

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

    override fun createComponent(): ScreenComponent {
        val activityComponent = DaggerActivityComponent.builder()
                .appComponent((application as App).appComponent)
                .activityModule(ActivityModule())
                .build()

        return DaggerMainScreenConfigurator_MainComponent.builder()
                .activityComponent(activityComponent)
                .viewModelStoreModule(ViewModelStoreModule(viewModelStore))
                .mainActivityModule(MainActivityModule(MainScreenRoute(intent)))
                .build()
    }
}