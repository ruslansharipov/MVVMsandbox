package ru.surfstudio.standard.f_main.bar.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_main.bar.IMainBarViewModel
import ru.surfstudio.standard.f_main.bar.MainBarFragmentView
import ru.surfstudio.standard.ui.navigation.MainBarRoute
import ru.surfstudio.standard.f_main.bar.MainBarViewModel
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configurator.Configurator
import ru.surfstudio.standard.ui.configurator.InjectionTarget
import ru.surfstudio.standard.ui.configurator.component.ScreenComponent
import ru.surfstudio.standard.ui.mvvm.ProviderViewModelFactory
import ru.surfstudio.standard.ui.screen.di.CustomScreenModule
import ru.surfstudio.standard.ui.screen.di.ViewModelStoreModule
import javax.inject.Provider

internal class MainBarScreenConfigurator(
        private val viewModelStore: ViewModelStore
) : Configurator {

    @PerScreen
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                MainBarModule::class,
                ViewModelStoreModule::class
            ]
    )
    internal interface MainBarComponent : ScreenComponent<MainBarFragmentView>

    @Module
    internal class MainBarModule(route: MainBarRoute) : CustomScreenModule<MainBarRoute>(route) {

        @Provides
        internal fun provideViewModel(
                viewModelStore: ViewModelStore,
                provider: Provider<MainBarViewModel>,
                route: MainBarRoute
        ): IMainBarViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                    route.getId(),
                    MainBarViewModel::class.java
            )
        }
    }

    override fun createComponent(activityComponent: ActivityComponent): ScreenComponent<out InjectionTarget> {
        return DaggerMainBarScreenConfigurator_MainBarComponent.builder()
                .activityComponent(activityComponent)
                .viewModelStoreModule(ViewModelStoreModule(viewModelStore))
                .mainBarModule(MainBarModule(MainBarRoute()))
                .build()
    }
}