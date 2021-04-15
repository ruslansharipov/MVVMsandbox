package ru.surfstudio.standard.f_main.container.di

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_main.container.IMainViewModel
import ru.surfstudio.standard.f_main.container.MainActivityView
import ru.surfstudio.standard.f_main.container.MainViewModel
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configuration.configurator.ActivityConfigurator
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.component.ScreenComponent
import ru.surfstudio.standard.ui.mvvm.ProviderViewModelFactory
import ru.surfstudio.standard.ui.navigation.MainRoute
import ru.surfstudio.standard.ui.screen.di.CustomScreenModule
import ru.surfstudio.standard.ui.screen.di.ViewModelStoreModule
import javax.inject.Provider

internal class MainScreenConfigurator: ActivityConfigurator() {

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
        internal fun provideViewModel(
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

    override fun createComponent(activityComponent: ActivityComponent, viewModelStore: ViewModelStore, args: Intent): ScreenComponent<out InjectionTarget> {
        return DaggerMainScreenConfigurator_MainComponent.builder()
                .activityComponent(activityComponent)
                .viewModelStoreModule(ViewModelStoreModule(viewModelStore))
                .mainModule(MainModule(MainRoute(args)))
                .build()
    }
}
