package ru.surfstudio.standard.f_main.di

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.application.app.App
import ru.surfstudio.standard.f_main.IMainViewModel
import ru.surfstudio.standard.f_main.MainActivityView
import ru.surfstudio.standard.f_main.MainViewModel
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.activity.di.ActivityModule
import ru.surfstudio.standard.ui.activity.di.DaggerActivityComponent
import ru.surfstudio.standard.ui.configurator.Configurator
import ru.surfstudio.standard.ui.configurator.InjectionTarget
import ru.surfstudio.standard.ui.configurator.component.ScreenComponent
import ru.surfstudio.standard.ui.mvvm.ProviderViewModelFactory
import ru.surfstudio.standard.ui.navigation.MainRoute
import ru.surfstudio.standard.ui.screen.di.CustomScreenModule
import ru.surfstudio.standard.ui.screen.di.ViewModelStoreModule
import javax.inject.Provider

internal class MainScreenConfigurator(
        private val application: Application,
        private val viewModelStore: ViewModelStore,
        private val intent: Intent
): Configurator {

    @PerScreen
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                MainModule::class,
                ViewModelStoreModule::class
            ]
    )
    internal interface MainComponent: ScreenComponent<MainActivityView>

    @Module
    internal class MainModule(route: MainRoute) : CustomScreenModule<MainRoute>(route) {

        @Provides
        fun provideViewModel(
                viewModelStore: ViewModelStore,
                provider: Provider<MainViewModel>,
                route: MainRoute
        ): IMainViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                    route.getId(),
                    MainViewModel::class.java
            )
        }
    }

    override fun createComponent(): ScreenComponent<out InjectionTarget> {
        val activityComponent = DaggerActivityComponent.builder()
                .appComponent((application as App).appComponent)
                .activityModule(ActivityModule())
                .build()

        return DaggerMainScreenConfigurator_MainComponent.builder()
                .activityComponent(activityComponent)
                .viewModelStoreModule(ViewModelStoreModule(viewModelStore))
                .mainModule(MainModule(MainRoute(intent)))
                .build()
    }
}
